package domain.meeting;

import dto.MeetingCreateDto;
import dto.MeetingUpdateDto;
import global.ErrorMessage;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import untils.InputParser;

public class Meeting {
    private final LocalDate date;
    private final MeetingTime meetingTime;
    private Long id;
    private String place;
    private String topic;

    private Meeting(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        this(null, date, meetingTime, topic, place);
    }

    private Meeting(Long id, LocalDate date, MeetingTime meetingTime, String topic, String place) {
        validate(date);
        this.id = id;
        this.date = date;
        this.meetingTime = meetingTime;
        this.topic = topic;
        this.place = place;
    }

    public static Meeting of(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        return new Meeting(date, meetingTime, topic, place);
    }

    public static Meeting of(Long id, LocalDate date, MeetingTime meetingTime, String topic, String place) {
        return new Meeting(id, date, meetingTime, topic, place);
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
        meetingUpdateDto.topic().ifPresent(topic -> this.topic = topic);
        meetingUpdateDto.place().ifPresent(place -> this.place = place);
    }

    public boolean isAfterNow() {
        LocalDateTime now = LocalDateTime.now();
        long duration = getBetweenDays();
        if (duration > 0) {
            return true;
        }
        return date.isEqual(now.toLocalDate()) && meetingTime.isStartTimeAfter(now.toLocalTime());
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
        return date.isEqual(now.toLocalDate()) && meetingTime.isEndTimeBefore(now.toLocalTime());
    }

    public boolean isSameById(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format("(ID: %d) %s %s / %s / %s", id, date,
                meetingTime.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")), topic, place);
    }
    
}
