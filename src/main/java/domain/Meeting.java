package domain;

import global.ErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;

public class Meeting {

    private final LocalDate date;
    private final MeetingTime meetingTime;
    private final String topic;
    private final String place;

    private Meeting(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        validate(date, meetingTime, topic, place);
        this.date = date;
        this.meetingTime = meetingTime;
        this.topic = topic;
        this.place = place;
    }

    public static Meeting of(LocalDate date, LocalTime startTime, LocalTime endTime, String topic, String place) {
        return new Meeting(date, MeetingTime.of(startTime, endTime), topic, place);
    }

    private void validate(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        validateDate(date);
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getMessage());
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public MeetingTime getMeetingTime() {
        return meetingTime;
    }

    public String getTopic() {
        return topic;
    }

    public String getPlace() {
        return place;
    }
}
