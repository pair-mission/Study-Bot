package global;

import global.utils.InputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static global.enums.ErrorMessage.BLANK_INPUT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("입력값이 빈 문자열이거나 공백이면 예외가 발생한다.")
    void validateBlankInput(String input) {
        // when, then
        assertThatThrownBy(() -> InputValidator.validateBlankInput(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(BLANK_INPUT.getMessage());
    }

}