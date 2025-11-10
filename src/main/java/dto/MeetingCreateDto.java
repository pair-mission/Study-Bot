package dto;

import static global.ErrorMessage.INVALID_DATE_PATTERN;
import static global.ErrorMessage.INVALID_TIME_PATTERN;
import static global.InputValidator.validateBlankInput;

public class MeetingCreateDto {

    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String TIME_PATTERN = "^([01]\\d|2[0-3]):(00|30)$";

    private final String date;
    private final String startTime;
    private final String endTime;
    private final String topic;
    private final String place;

    private MeetingCreateDto(String date, String startTime, String endTime, String topic, String place) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.topic = topic;
        this.place = place;
    }

    public static MeetingCreateDto of(String date, String startTime, String endTime, String topic, String place) {
        validateDate(date);
        validateTime(startTime);
        validateTime(endTime);
        return new MeetingCreateDto(date, startTime, endTime, topic, place);
    }

    private static void validateDate(String dateInput) {
        validateBlankInput(dateInput);
        validateDatePattern(dateInput);
    }

    private static void validateDatePattern(String dateInput) {
        if (!dateInput.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException(INVALID_DATE_PATTERN.getMessage());
        }
    }

    private static void validateTime(String timeInput) {
        validateBlankInput(timeInput);
        validateTimePattern(timeInput);
    }

    private static void validateTimePattern(String timeInput) {
        if (!timeInput.matches(TIME_PATTERN)) {
            throw new IllegalArgumentException(INVALID_TIME_PATTERN.getMessage());
        }
    }
}
