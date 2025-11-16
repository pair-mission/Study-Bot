package service;

import domain.meeting.Meeting;
import domain.meeting.MeetingParticipant;
import domain.meeting.MeetingRepository;
import domain.meeting.ParticipantInMemoryRepository;
import domain.member.Member;
import domain.member.MemberRepository;
import domain.member.Role;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import global.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;
    private final ParticipantInMemoryRepository participantRepository;

    public MeetingService(MeetingRepository meetingRepository, MemberRepository memberRepository,
                          ParticipantInMemoryRepository participantRepository) {
        this.meetingRepository = meetingRepository;
        this.memberRepository = memberRepository;
        this.participantRepository = participantRepository;
    }

    public void createMeeting(MeetingCreateDto meetingCreateDto, Member member) {
        Meeting meeting = Meeting.toEntity(meetingCreateDto);
        meetingRepository.save(meeting);
        MeetingParticipant participant = MeetingParticipant.toEntity(Role.HOST, member, meeting);
        participantRepository.save(participant);
    }

    public void updateMeeting(Member member, Long meetingId, MeetingUpdateDto meetingUpdateDto) {
        if (!participantRepository.isHost(member.getId(), meetingId)) { // 모임장인지 확인
            throw new IllegalArgumentException(ErrorMessage.NOT_HOST.getMessage());
        }

        Meeting meeting = meetingRepository.findById(meetingId);

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

}
