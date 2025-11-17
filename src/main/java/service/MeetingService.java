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

    public void createMeeting(MeetingCreateDto meetingCreateDto, String nickname) {
        Member member = memberRepository.findByNickName(nickname);
        Meeting meeting = Meeting.toEntity(meetingCreateDto);
        meetingRepository.save(meeting);
        MeetingParticipant participant = MeetingParticipant.of(Role.HOST, member, meeting);
        participantRepository.save(participant);
    }

    public void updateMeeting(String nickname, Long meetingId, MeetingUpdateDto meetingUpdateDto) {
        Member member = memberRepository.findByNickName(nickname);
        this.isHost(member, meetingId);
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

    public void deleteMeeting(Long meetingId, String nickname) {
        Member host = memberRepository.findByNickName(nickname);
        this.isHost(host, meetingId);
        meetingRepository.delete(meetingId);
    }


    private boolean isHost(Member hostMember, Long meetingId) {
        if (!participantRepository.isHost(hostMember.getId(), meetingId)) { // 모임장인지 확인
            throw new IllegalArgumentException(ErrorMessage.NOT_HOST.getMessage());
        }
        return true;
    }

    public List<Meeting> getMyMeetings(String nickname) {
        Member member = memberRepository.findByNickName(nickname);
        return participantRepository.findMeetingsByMember(member.getId());
    }

    public void createParticipant(Long meetingId, String nickname) {
        Member member = memberRepository.findByNickName(nickname);
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
