package domain;

import java.time.LocalTime;

public class MeetingTime {

    private LocalTime startTime;
    private LocalTime endTime;

    private MeetingTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static MeetingTime of(LocalTime startTime, LocalTime endTime) {
        return new MeetingTime(startTime, endTime);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
