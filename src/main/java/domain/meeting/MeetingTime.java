package domain.meeting;

import java.time.LocalTime;

public class MeetingTime {
    private final LocalTime startTime;
    private final LocalTime endTime;

    private MeetingTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static MeetingTime of(LocalTime startTime, LocalTime endTime) {
        validate(startTime, endTime);
        return new MeetingTime(startTime, endTime);
    }

    private static void validate(LocalTime startTime, LocalTime endTime) {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isStartTimeAfter(LocalTime time) {
        return startTime.isAfter(time);
    }

    public boolean isEndTimeBefore(LocalTime time) {
        return endTime.isBefore(time);
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
