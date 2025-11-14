package domain;

import dto.MeetingCreateDto;
import global.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;

public class Meeting {
    private Long id;
    private LocalDate date;
    private MeetingTime meetingTime;
    private String place;
    private String topic;

    private Meeting(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        validate(date);
        this.date = date;
        this.meetingTime = meetingTime;
        this.topic = topic;
        this.place = place;
    }

    public static Meeting of(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        return new Meeting(date, meetingTime, topic, place);
    }

    public static Meeting toEntity(MeetingCreateDto meetingCreateDto){
        return of(
                InputParser.parseDate(meetingCreateDto.date()),
                InputParser.parseTime(meetingCreateDto.startTime(),meetingCreateDto.endTime()),
                meetingCreateDto.topic(),
                meetingCreateDto.place()
        );
    }

    private void validate(LocalDate date) {
        validateDate(date);
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getMessage());
        }
    }
}
