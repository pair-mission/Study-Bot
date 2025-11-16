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

import java.util.ArrayList;
import java.util.List;

import static global.ErrorMessage.MEMBER_NOT_FOUND;

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

    public void updateMeeting(String nickname, Long meetingId, MeetingUpdateDto meetingUpdateDto) {
        if (!memberRepository.existsBy(nickname)) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
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
