package dto;

import domain.meeting.Meeting;

import java.util.List;

public record MeetingAttendanceDto(
        Meeting meeting,
        List<String> attenders
) {
    public static MeetingAttendanceDto of(Meeting meeting, List<String> attenders) {
        return new MeetingAttendanceDto(meeting, attenders);
    }
}
