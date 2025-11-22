package domain.meeting;

import dto.MeetingCreateDto;
import dto.MeetingUpdateDto;
import global.enums.ErrorMessage;
import global.utils.parser.InputParser;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Meeting {

    private final LocalDate date;
    private final MeetingTime meetingTime;
    private final Long id;
    private String place;
    private String topic;

    private Meeting(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        this(null, date, meetingTime, topic, place);
    }

    private Meeting(Long id, LocalDate date, MeetingTime meetingTime, String topic, String place) {
        validate(date, meetingTime, topic, place);
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

    public static Meeting of(Long id, Meeting meeting) {
        LocalDate date = meeting.getDate();
        MeetingTime meetingTime = meeting.getMeetingTime();
        String topic = meeting.getTopic();
        String place = meeting.getPlace();

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

    private void validate(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        validateNullOrEmpty(date, meetingTime, topic, place);
        validateDate(date);
    }

    private void validateNullOrEmpty(LocalDate date, MeetingTime meetingTime, String... values) {
        if (date == null || meetingTime == null) {
            throw new IllegalArgumentException(ErrorMessage.NULL_OR_EMPTY.getMessage());
        }

        for (String value : values) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException(ErrorMessage.NULL_OR_EMPTY.getMessage());
            }
        }
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

    public boolean isAttendanceAvailable() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(date, meetingTime.getStartTime()).minusMinutes(10); // 출석 시작 시간
        LocalDateTime end = LocalDateTime.of(date, meetingTime.getStartTime()).plusMinutes(10); // 출석 종료 시간

        return now.isBefore(end) && now.isAfter(start);
    }

    private long getBetweenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = LocalDateTime.of(date, meetingTime.getStartTime());
        return Duration.between(now, startDateTime).toDays();
    }

    public boolean isRemindMeeting(int remindDays) {
        LocalDate startDate = LocalDate.now().plusDays(remindDays);
        return this.date.isEqual(startDate);
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

    public LocalDateTime getStartTime() {
        return LocalDateTime.of(date, meetingTime.getStartTime());
    }

    public Long getId() {
        return id;
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
