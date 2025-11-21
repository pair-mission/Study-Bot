package service;

import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import global.enums.ErrorMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import repository.meeting.MeetingRepository;
import repository.participant.ParticipantInMemoryRepository;

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
        Meeting savedMeeting = meetingRepository.save(meeting);
        MeetingParticipant participant = MeetingParticipant.of(Role.HOST, member, savedMeeting);
        participantRepository.save(participant);
    }

    public void updateMeeting(MeetingUpdateDto meetingUpdateDto, Member member) {
        Meeting meeting = meetingRepository.findById(meetingUpdateDto.meetingId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEETING_NOT_FOUND.getMessage()));
        this.isHost(member, meetingUpdateDto.meetingId());
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
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEETING_NOT_FOUND.getMessage()));
        this.isHost(member, meeting.getId());
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
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEETING_NOT_FOUND.getMessage()));

        participantRepository.findParticipantByMeetingIdAndMemberId(meeting.getId(), member.getId())
                .ifPresent(participant -> {
                    throw new IllegalArgumentException(ErrorMessage.ALREADY_PARTICIPANT.getMessage());
                });

        MeetingParticipant participant = MeetingParticipant.of(Role.MEMBER, member, meeting);

        participantRepository.save(participant);
    }

    public List<String> getAllParticipants(Long meetingId) {
        meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEETING_NOT_FOUND.getMessage()));

        return participantRepository.findAllParticipantsByMeetingId(meetingId).stream()
                .map(participant -> participant.getMember().getNickname())
                .toList();
    }

    public List<Meeting> findRemindMeetings(Long memberId, int remindDays) {
        return participantRepository.findMeetingsByMember(memberId).stream()
                .filter(meeting -> meeting.isRemindMeeting(remindDays))
                .toList();
    }

    public List<Meeting> findAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting getMyNextMeeting(Long memberId) {

        LocalDateTime now = LocalDateTime.now();

        return participantRepository.findMeetingsByMember(memberId).stream()
                .filter(meeting -> meeting.getStartTime().isAfter(now))
                .min(Comparator.comparing(Meeting::getStartTime))
                .orElse(null);
    }
}
