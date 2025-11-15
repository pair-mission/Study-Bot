package domain.meeting;

import dto.MeetingCreateDto;
import dto.MeetingUpdateDto;
import global.ErrorMessage;
import java.time.LocalDate;
import untils.InputParser;

public class Meeting {
    private final LocalDate date;
    private final MeetingTime meetingTime;
    private Long id;
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

    public static Meeting toEntity(MeetingCreateDto meetingCreateDto) {
        return of(
                InputParser.parseDate(meetingCreateDto.date()),
                InputParser.parseTime(meetingCreateDto.startTime(), meetingCreateDto.endTime()),
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

    public void compareAndChange(MeetingUpdateDto meetingUpdateDto) {
        String newTopic = meetingUpdateDto.topic();
        String newPlace = meetingUpdateDto.place();

        if (!newTopic.equals(this.topic)) {
            this.topic = newTopic;
        }
        if (!newPlace.equals(this.place)) {
            this.place = newPlace;
        }
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public String getPlace() {
        return place;
    }
}