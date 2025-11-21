package service;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import dto.MeetingUpdateDto;
import global.enums.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.meeting.MeetingInMemoryRepository;
import repository.member.MemberInMemoryRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantInMemoryRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MeetingServiceTest {

    private MeetingService meetingService;
    private MeetingInMemoryRepository meetingInMemoryRepository;
    private MemberRepository memberRepository;
    private ParticipantInMemoryRepository participantInMemoryRepository;
    private Member host;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meetingInMemoryRepository = new MeetingInMemoryRepository();
        memberRepository = new MemberInMemoryRepository();
        participantInMemoryRepository = new ParticipantInMemoryRepository();
        meetingService = new MeetingService(meetingInMemoryRepository, participantInMemoryRepository);

        host = Member.of(1L, "호스트");
        meeting = meetingInMemoryRepository.save(
                Meeting.of(LocalDate.of(2025, 12, 31),
                        MeetingTime.of(LocalTime.of(19, 0), LocalTime.of(21, 0)),
                        "자바스터디", "스터디룸A")
        );
        participantInMemoryRepository.save(MeetingParticipant.of(Role.HOST, host, meeting));
    }

    @DisplayName("존재하지 않는 모임이면 예외가 발생한다")
    @Test
    void 존재하지_않는_모임이면_예외가_발생한다() {

        String userInput = "6, 코테1회차, 스터디룸B";

        MeetingUpdateDto meetingUpdateDto = MeetingUpdateDto.from(userInput);

        assertThatThrownBy(() -> meetingService.updateMeeting(meetingUpdateDto, host))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEETING_NOT_FOUND.getMessage());

    }

    @DisplayName("모임장이 아닌 사용자가 수정하면 예외가 발생한다")
    @Test
    void 모임장이_아니면_예외() {
        Meeting meeting = meetingInMemoryRepository.save(
                Meeting.of(LocalDate.of(2025, 12, 31),
                        MeetingTime.of(LocalTime.of(19, 0), LocalTime.of(21, 0)),
                        "자바스터디", "스터디룸A")
        );

        Member host = Member.of(1L, "호스트");
        Member guest = Member.of(2L, "게스트");

        participantInMemoryRepository.save(MeetingParticipant.of(Role.HOST, host, meeting));
        participantInMemoryRepository.save(MeetingParticipant.of(Role.MEMBER, guest, meeting));

        String userInput = "0, 코테1회차, 스터디룸B";

        MeetingUpdateDto meetingUpdateDto = MeetingUpdateDto.from(userInput);

        // when & then
        assertThatThrownBy(() -> meetingService.updateMeeting(meetingUpdateDto, guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_HOST.getMessage());

    }

    @DisplayName("모임 삭제 시 모임장이 아니면 예외가 발생한다")
    @Test
    void 모임_삭제시_모임장이_아니면_예외가_발생한다() {

        Meeting meeting = meetingInMemoryRepository.save(
                Meeting.of(LocalDate.of(2025, 12, 31),
                        MeetingTime.of(LocalTime.of(19, 0), LocalTime.of(21, 0)),
                        "자바스터디", "스터디룸A")
        );
        Member guest = Member.of(2L, "게스트");
        participantInMemoryRepository.save(MeetingParticipant.of(Role.MEMBER, guest, meeting));

        assertThatThrownBy(() -> meetingService.deleteMeeting(meeting.getId(), guest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.NOT_HOST.getMessage());
    }

    // TODO 추가 구현 필요. 예외 발생 안하고 있음.
    @Test
    @DisplayName("참여자 등록 - 이미 참여 중인 경우 예외가 발생한다.")
    void testRegisterParticipantWhenAlreadyExist() {
        // given
        // member가 meeting에 참여한 상태일 때
        Member member = memberRepository.save(Member.from("제이"));
        MeetingParticipant participant = MeetingParticipant.of(Role.MEMBER, host, meeting);
        participantInMemoryRepository.save(participant);

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
        Member member = memberRepository.save(Member.from("제이"));

        // when, then
        assertThatThrownBy(() -> meetingService.createParticipant(notExistMeetingId, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEETING_NOT_FOUND.getMessage());
    }

    // TODO getAllParticipants 수정해야함
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
