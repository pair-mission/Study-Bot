package domain.attendance;

import domain.participant.MeetingParticipant;

public class Attendance {

    private final MeetingParticipant participant;
    private final AttendanceStatus status;
    private Long id;

    private Attendance(MeetingParticipant participant, AttendanceStatus status) {
        this.participant = participant;
        this.status = status;
    }

    private Attendance(Long id, MeetingParticipant participant, AttendanceStatus status) {
        this.id = id;
        this.participant = participant;
        this.status = status;
    }

    public static Attendance of(MeetingParticipant participant, AttendanceStatus status) {
        return new Attendance(participant, status);
    }

    public static Attendance of(Long id, Attendance attendance) {
        return new Attendance(id, attendance.getParticipant(), attendance.getStatus());
    }

    public Long getId() {
        return id;
    }

    public MeetingParticipant getParticipant() {
        return participant;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
