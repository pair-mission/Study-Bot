package global;

import static global.ErrorMessage.*;

public class InputValidator {

    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String TIME_PATTERN = "^([01]\\d|2[0-3]):(00|30)$";

    public static void validateDate(String dateInput) {
        validateBlankInput(dateInput);
        validateDatePattern(dateInput);
    }

    private static void validateDatePattern(String dateInput) {
        if (!dateInput.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException(INVALID_DATE_PATTERN.getMessage());
        }
    }

    private static void validateBlankInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(BLANK_INPUT.getMessage());
        }
    }

    public static void validateTime(String timeInput) {
        validateBlankInput(timeInput);
        validateTimePattern(timeInput);
    }

    private static void validateTimePattern(String timeInput) {
        if (!timeInput.matches(TIME_PATTERN)) {
            throw new IllegalArgumentException(INVALID_TIME_PATTERN.getMessage());
        }
    }

}
