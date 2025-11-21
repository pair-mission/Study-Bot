package service;

import domain.attendance.Attendance;
import domain.attendance.AttendanceStatus;
import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import dto.MeetingAttendanceDto;
import global.enums.ErrorMessage;
import repository.attendance.AttendanceRepository;
import repository.participant.ParticipantInMemoryRepository;

import java.util.List;

public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ParticipantInMemoryRepository participantInMemoryRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             ParticipantInMemoryRepository participantInMemoryRepository) {
        this.attendanceRepository = attendanceRepository;
        this.participantInMemoryRepository = participantInMemoryRepository;
    }


    public void createAttendance(Long meetingId, Member loginMember) {

        MeetingParticipant participant = participantInMemoryRepository.findParticipantByMeetingIdAndMemberId(meetingId, loginMember.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ALREADY_PARTICIPANT.getMessage()));

        validateIsNotParticipant(participant);
        validateIsAttendanceAvailable(participant.getMeeting());

        Attendance attendance = Attendance.of(participant, AttendanceStatus.ATTENDANCE);

        attendanceRepository.save(attendance);
    }

    public List<MeetingAttendanceDto> findAllAttendance(List<Meeting> meetings) {
        return meetings.stream()
                .map(this::createAttendanceDto)
                .toList();
    }

    private void validateIsNotParticipant(MeetingParticipant participant) {
        if (participant == null) {
            throw new IllegalArgumentException(ErrorMessage.MEMBER_NOT_PARTICIPANT.getMessage());
        }
    }

    private void validateIsAttendanceAvailable(Meeting meeting) {
        if (!meeting.isAttendanceAvailable()) {
            throw new IllegalArgumentException(ErrorMessage.ATTENDANCE_UNAVAILABLE.getMessage());
        }
    }

    private MeetingAttendanceDto createAttendanceDto(Meeting meeting) {
        List<MeetingParticipant> allParticipants =
                participantInMemoryRepository.findAllParticipantsByMeetingId(meeting.getId());

        List<String> attenderNames = attendanceRepository.findAttendersByParticipants(allParticipants).stream()
                .map(attendance -> attendance.getParticipant().getMember().getNickname())
                .toList();

        return MeetingAttendanceDto.of(meeting, attenderNames);
    }
}
