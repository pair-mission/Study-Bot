package domain;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        attendanceInMemoryRepository = new AttendanceInMemoryRepository();
        participantInMemoryRepository = new ParticipantInMemoryRepository();
        meetingInMemoryRepository = new MeetingInMemoryRepository();
        attendanceService = new AttendanceService(attendanceInMemoryRepository, participantInMemoryRepository);

        Meeting meeting = meetingInMemoryRepository.save(
                Meeting.of(LocalDate.of(2025, 11, 21),
                        MeetingTime.of(LocalTime.of(19, 40), LocalTime.of(21, 0)),
                        "자바스터디", "스터디룸A")
        );
        member = Member.of(2L, "게스트");
        participantInMemoryRepository.save(MeetingParticipant.of(Role.MEMBER, member, meeting));
        attendanceService.createAttendance(meeting.getId(), member);
    }
}
