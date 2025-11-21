package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import global.enums.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.attendance.AttendanceInMemoryRepository;
import repository.meeting.MeetingInMemoryRepository;
import repository.participant.ParticipantInMemoryRepository;
import service.AttendanceService;

public class AttendanceServiceTest {

    private AttendanceInMemoryRepository attendanceInMemoryRepository;
    private ParticipantInMemoryRepository participantInMemoryRepository;
    private MeetingInMemoryRepository meetingInMemoryRepository;
    private AttendanceService attendanceService;
    private Member member;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        attendanceInMemoryRepository = new AttendanceInMemoryRepository();
        participantInMemoryRepository = new ParticipantInMemoryRepository();
        meetingInMemoryRepository = new MeetingInMemoryRepository();
        attendanceService = new AttendanceService(attendanceInMemoryRepository, participantInMemoryRepository);

        meeting = meetingInMemoryRepository.save(
                Meeting.of(LocalDate.now(),
                        MeetingTime.of(LocalTime.now(), LocalTime.of(23, 0)),
                        "자바스터디", "스터디룸A")
        );
        member = Member.of(2L, "게스트");
        participantInMemoryRepository.save(MeetingParticipant.of(Role.MEMBER, member, meeting));
        attendanceService.createAttendance(meeting.getId(), member);
    }

    @DisplayName("이미 출석한 모임이면 예외가 발생한다")
    @Test
    void createAlreadyAttended() {

        assertThatThrownBy(() -> attendanceService.createAttendance(meeting.getId(), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ALREADY_ATTENDED.getMessage());

    }

    @DisplayName("모임 시작 10분 전 후가 아니면 예외가 발생한다")
    @Test
    void createAttendanceUnAvailable() {
        meeting = meetingInMemoryRepository.save(
                Meeting.of(LocalDate.now().plusDays(10),
                        MeetingTime.of(LocalTime.of(19, 0), LocalTime.of(23, 0)),
                        "자바스터디", "스터디룸A")
        );
        member = Member.of(2L, "게스트");
        participantInMemoryRepository.save(MeetingParticipant.of(Role.MEMBER, member, meeting));

        assertThatThrownBy(() -> attendanceService.createAttendance(meeting.getId(), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ATTENDANCE_UNAVAILABLE.getMessage());

    }

    @DisplayName("참여 중이 아닌 모임에 출석 시 예외가 발생한다")
    @Test
    void notParticipantAttendance() {

        Member newMember = Member.of(3L, "게스트");

        assertThatThrownBy(() -> attendanceService.createAttendance(meeting.getId(), newMember))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEMBER_NOT_PARTICIPANT.getMessage());

    }

    @DisplayName("해당 모임이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void meetingNotFound() {

        assertThatThrownBy(() -> attendanceService.createAttendance(12L, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.MEMBER_NOT_PARTICIPANT.getMessage());
    }

}
