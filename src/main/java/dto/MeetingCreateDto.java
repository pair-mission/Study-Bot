package dto;

import global.InputValidator;

import static global.ErrorMessage.INVALID_DATE_PATTERN;
import static global.ErrorMessage.INVALID_TIME_PATTERN;
import static global.InputValidator.validateBlankInput;
import static global.Pattern.DATE_PATTERN;
import static global.Pattern.TIME_PATTERN;

public class MeetingCreateDto {

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
        validate(date, startTime, endTime, topic, place);
        return new MeetingCreateDto(date, startTime, endTime, topic, place);
    }

    private static void validate(String date, String startTime, String endTime, String topic, String place) {
        validateDate(date);
        validateTime(startTime);
        validateTime(endTime);
        InputValidator.validateBlankInput(topic);
        InputValidator.validateBlankInput(place);
    }

    private static void validateDate(String dateInput) {
        validateBlankInput(dateInput);
        validateDatePattern(dateInput);
    }

    private static void validateDatePattern(String dateInput) {
        if (!dateInput.matches(DATE_PATTERN.getValue())) {
            throw new IllegalArgumentException(INVALID_DATE_PATTERN.getMessage());
        }
    }

    private static void validateTime(String timeInput) {
        validateBlankInput(timeInput);
        validateTimePattern(timeInput);
    }

    private static void validateTimePattern(String timeInput) {
        if (!timeInput.matches(TIME_PATTERN.getValue())) {
            throw new IllegalArgumentException(INVALID_TIME_PATTERN.getMessage());
        }
    }

}
