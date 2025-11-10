package global;

import static global.ErrorMessage.BLANK_INPUT;
import static global.ErrorMessage.INVALID_DATE_PATTERN;

public class InputValidator {

    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

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

}
