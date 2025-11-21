package service;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import global.enums.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.meeting.MeetingInMemoryRepository;
import repository.meeting.MeetingRepository;
import repository.member.MemberInMemoryRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantInMemoryRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MeetingServiceTest {

    private final MemberRepository memberRepository = new MemberInMemoryRepository();
    private final MeetingRepository meetingRepository = new MeetingInMemoryRepository();
    private final ParticipantInMemoryRepository participantRepository = new ParticipantInMemoryRepository();
    private final MeetingService meetingService = new MeetingService(meetingRepository, participantRepository);

    private Member member;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        MeetingTime meetingTime = MeetingTime.of(LocalTime.now(), LocalTime.now().plusHours(2));

        meeting = meetingRepository.save(Meeting.of(LocalDate.now(), meetingTime, "topic", "place"));
        member = memberRepository.save(Member.from("제이"));
    }

    // TODO 추가 구현 필요. 예외 발생 안하고 있음.
    @Test
    @DisplayName("참여자 등록 - 이미 참여 중인 경우 예외가 발생한다.")
    void testRegisterParticipantWhenAlreadyExist() {
        // given
        // member가 meeting에 참여한 상태일 때
        MeetingParticipant participant = MeetingParticipant.of(Role.MEMBER, member, meeting);
        participantRepository.save(participant);

        // when, then
        // 다시 참여하려는 경우 예외 발생해야함
        assertThatThrownBy(() -> meetingService.createParticipant(meeting.getId(), member))
                .isInstanceOf(IllegalArgumentException.class);
//                .hasMessageContaining(ErrorMessage.???.getMessage()); // ErrorMessage 없음
    }

    @Test
    @DisplayName("참여자 등록 - 참여할 모임이 없는 경우 예외가 발생한다.")
    void testRegisterParticipantWhenMeetingNotFound() {
        // given
        Long notExistMeetingId = 100L; // 존재하지 않는 모임의 id

        // when, then
        assertThatThrownBy(() -> meetingService.createParticipant(notExistMeetingId, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEETING_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("참여자 조회 - 조회하는 모임이 존재하지 않을 경우 예외가 발생한다.")
    void testGetParticipantsWhenMeetingNotFound() {
        // given
        Long notExistMeetingId = 100L;

        // when, then
        assertThatThrownBy(() -> meetingService.getAllParticipants(notExistMeetingId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEETING_NOT_FOUND.getMessage());
    }

}