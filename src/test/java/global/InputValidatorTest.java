package global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static global.ErrorMessage.BLANK_INPUT;
import static global.ErrorMessage.INVALID_DATE_PATTERN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"2025:11:10", "2025.11.10", "2025-9-1"})
    @DisplayName("날짜의 형식을 \"YYYY-MM-dd\"로 입력하지 않으면 예외가 발생한다.")
    void validateDatePattern(String dateInput) {
        // when, then
        assertThatThrownBy(() -> InputValidator.validateDate(dateInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_DATE_PATTERN.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("날짜 입력값이 빈 문자열이거나 공백이면 예외가 발생한다.")
    void validateBlankDateInput(String dateInput) {
        // when, then
        assertThatThrownBy(() -> InputValidator.validateDate(dateInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(BLANK_INPUT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"00-46", "7:5", "23/06"})
    @DisplayName("시간의 형식을 \"HH:mm\"으로 입력하지 않으면 예외가 발생한다.")
    void testMeetingTimeFormat(String meetingTimeInput) {
        // when
        assertThatThrownBy(() -> InputValidator.validateTime(meetingTimeInput))
                .isInstanceOf(IllegalArgumentException.class);
    }
}