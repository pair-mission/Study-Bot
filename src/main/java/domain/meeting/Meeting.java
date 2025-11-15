package domain.meeting;

import dto.MeetingCreateDto;
import dto.MeetingUpdateDto;
import global.ErrorMessage;
import untils.InputParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
                InputParser.parseToDate(meetingCreateDto.date()),
                InputParser.parseToTime(meetingCreateDto.startTime(), meetingCreateDto.endTime()),
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

    public boolean isAfterNow() {
        LocalDateTime now = LocalDateTime.now();
        long duration = getBetweenDays();
        if (duration > 0) {
            return true;
        }
        if (date.isEqual(now.toLocalDate()) && meetingTime.isStartTimeAfter(now.toLocalTime())) {
            return true;
        }
        return false;
    }

    private long getBetweenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = LocalDateTime.of(date, meetingTime.getStartTime());
        return Duration.between(now, startDateTime).toDays();
    }

    public boolean isTomorrowMeeting() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return this.date.isEqual(tomorrow);
    }

    public boolean isMeetingOver() {
        LocalDateTime now = LocalDateTime.now();
        long duration = getBetweenDays();
        if (duration < 0) {
            return true;
        }
        if (date.isEqual(now.toLocalDate()) && meetingTime.isEndTimeBefore(now.toLocalTime())) {
            return true;
        }
        return false;
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