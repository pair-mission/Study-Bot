package global;

import static global.ErrorMessage.BLANK_INPUT;

public class InputValidator {

    public static void validateBlankInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(BLANK_INPUT.getMessage());
        }
    }

}
