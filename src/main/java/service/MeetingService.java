package service;

import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import global.enums.ErrorMessage;
import repository.meeting.MeetingRepository;
import repository.participant.ParticipantInMemoryRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final ParticipantInMemoryRepository participantRepository;

    public MeetingService(MeetingRepository meetingRepository,
                          ParticipantInMemoryRepository participantRepository) {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
    }

    public void createMeeting(MeetingCreateDto meetingCreateDto, Member member) {
        Meeting meeting = Meeting.toEntity(meetingCreateDto);
        meetingRepository.save(meeting);
        MeetingParticipant participant = MeetingParticipant.of(Role.HOST, member, meeting);
        participantRepository.save(participant);
    }

    public void updateMeeting(MeetingUpdateDto meetingUpdateDto, Member member) {
        this.isHost(member, meetingUpdateDto.meetingId());
        Meeting meeting = meetingRepository.findById(meetingUpdateDto.meetingId());
        meeting.compareAndChange(meetingUpdateDto);
    }

    public List<MeetingInfoDto> getAllMeetings() {
        List<MeetingInfoDto> meetingInfos = new ArrayList<>();
        List<Meeting> meetings = meetingRepository.findAll();

        for (Meeting meeting : meetings) {
            List<Member> participants = participantRepository.findAllParticipantsByMeetingId(meeting.getId()).stream()
                    .map(MeetingParticipant::getMember).toList();
            meetingInfos.add(MeetingInfoDto.of(meeting, participants.size()));
        }

        return meetingInfos;
    }

    public void deleteMeeting(Long meetingId, Member member) {
        this.isHost(member, meetingId);
        meetingRepository.delete(meetingId);
    }

    private void isHost(Member hostMember, Long meetingId) {
        if (!participantRepository.isHost(hostMember.getId(), meetingId)) { // 모임장인지 확인
            throw new IllegalArgumentException(ErrorMessage.NOT_HOST.getMessage());
        }
    }

    public List<Meeting> getMyMeetings(Member member) {
        return participantRepository.findMeetingsByMember(member.getId());
    }

    public void createParticipant(Long meetingId, Member member) {
        Meeting meeting = meetingRepository.findById(meetingId);
        MeetingParticipant participant = MeetingParticipant.of(Role.MEMBER, member, meeting);
        participantRepository.save(participant);
    }

    public List<String> getAllParticipants(Long meetingId) {
        return participantRepository.findAllParticipantsByMeetingId(meetingId).stream()
                .map(participant -> participant.getMember().getNickname())
                .toList();
    }

    public List<Meeting> findByTomorrowMeetings(Long memberId) {
        return participantRepository.findMeetingsByMember(memberId).stream()
                .filter(Meeting::isTomorrowMeeting)
                .toList();
    }
}
