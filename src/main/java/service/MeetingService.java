package service;

import static global.ErrorMessage.MEMBER_NOT_FOUND;

import domain.meeting.Meeting;
import domain.meeting.MeetingParticipant;
import domain.meeting.MeetingRepository;
import domain.meeting.ParticipantInMemoryRepository;
import domain.member.MemberRepository;
import domain.member.Role;
import dto.MeetingCreateDto;
import dto.MeetingUpdateDto;
import java.io.IOException;

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

    public void createMeeting(MeetingCreateDto meetingCreateDto, Long hostId) {
        Meeting meeting = Meeting.toEntity(meetingCreateDto);
        meetingRepository.save(meeting);
        MeetingParticipant participant = MeetingParticipant.toEntity(Role.HOST, hostId, meeting.getId());
        participantRepository.save(participant);
    }

    public void updateMeeting(String nickname, Long meetingId, MeetingUpdateDto meetingUpdateDto) throws IOException {
        if (!memberRepository.existsBy(nickname)) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }
        Meeting meeting = meetingRepository.findById(meetingId);
        meeting.compareAndChange(meetingUpdateDto);
    }

}
