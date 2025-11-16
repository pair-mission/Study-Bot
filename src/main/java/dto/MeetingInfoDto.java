package dto;

import domain.meeting.Meeting;

public record MeetingInfoDto(
        String meetingInfo,
        int participantsCount
) {
    public static MeetingInfoDto of(Meeting meeting, int participantsCount) {
        return new MeetingInfoDto(meeting.toString(), participantsCount);
    }
}