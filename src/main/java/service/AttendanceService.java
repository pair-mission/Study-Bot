package service;

import domain.attendance.Attendance;
import domain.attendance.AttendanceStatus;
import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import global.enums.ErrorMessage;
import repository.attendance.AttendanceRepository;
import repository.participant.ParticipantInMemoryRepository;

public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ParticipantInMemoryRepository participantInMemoryRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             ParticipantInMemoryRepository participantInMemoryRepository) {
        this.attendanceRepository = attendanceRepository;
        this.participantInMemoryRepository = participantInMemoryRepository;
    }


    public Attendance createAttendance(Long meetingId, Member loginMember) {

        MeetingParticipant participant = participantInMemoryRepository.findParticipantsByMeetingIdAndMemberId(meetingId,
                loginMember.getId());

        if (participant == null) {
            throw new IllegalArgumentException(ErrorMessage.MEMBER_NOT_PARTICIPANT.getMessage());
        }

        Meeting meeting = participant.getMeeting();

        if (!meeting.isAttendanceAvailable()) {
            throw new IllegalArgumentException(ErrorMessage.ATTENDANCE_UNAVAILABLE.getMessage());
        }

        Attendance attendance = Attendance.of(participant, AttendanceStatus.ATTENDANCE);

        return attendanceRepository.save(attendance);

    }
}
