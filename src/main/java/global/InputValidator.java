package global;

import static global.ErrorMessage.BLANK_INPUT;
import static global.ErrorMessage.INVALID_DATE_PATTERN;
import static global.Pattern.DATE_PATTERN;
import static global.Pattern.TIME_PATTERN;

public class InputValidator {

    public static void validateBlankInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(BLANK_INPUT.getMessage());
        }
    }

    public static void validateDate(String dateInput) {
        validateBlankInput(dateInput);
        validateDatePattern(dateInput);
    }

    private static void validateDatePattern(String dateInput) {
        if (!dateInput.matches(DATE_PATTERN.getValue())) {
            throw new IllegalArgumentException(INVALID_DATE_PATTERN.getMessage());
        }
    }

    public static void validateTime(String timeInput) {
        validateBlankInput(timeInput);
        validateTimePattern(timeInput);
    }

    private static void validateTimePattern(String timeInput) {
        if (!timeInput.matches(TIME_PATTERN.getValue())) {
            throw new IllegalArgumentException(INVALID_DATE_PATTERN.getMessage());
        }
    }
}
