package service;

import domain.Meeting;
import dto.MeetingCreateDto;
import dto.MeetingUpdateDto;
import repository.MeetingRepository;
import repository.MemberRepository;

import static global.ErrorMessage.MEMBER_NOT_FOUND;

public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    public MeetingService(MeetingRepository meetingRepository, MemberRepository memberRepository) {
        this.meetingRepository = meetingRepository;
        this.memberRepository = memberRepository;
    }

    public void save(MeetingCreateDto meetingCreateDto) {
        Meeting meeting = Meeting.toEntity(meetingCreateDto);
        meetingRepository.save(meeting);
    }

    public void updateMeeting(String nickname, Long meetingId, MeetingUpdateDto meetingUpdateDto) {
        if (!memberRepository.existsBy(nickname)) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }
        Meeting meeting = meetingRepository.findById(meetingId);
        meeting.compareAndChange(meetingUpdateDto);
    }

}
