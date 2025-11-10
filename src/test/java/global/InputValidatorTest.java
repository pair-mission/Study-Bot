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

    @ParameterizedTest
    @ValueSource(strings = {"25:00:00", "50:00:00"})
    @DisplayName("시간(Hour)는 24시 표기법이어야 한다.")
    void testMeetingTimeHour(String input) {
        // given, when
        assertThatThrownBy(() -> InputValidator.validateTime(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"23:10", "22:20"})
    @DisplayName("분(Minute)은 30분 단위여야 한다.")
    void testMeetingTimeMinute(String input) {
        // given, when
        assertThatThrownBy(() -> InputValidator.validateTime(input))
                .isInstanceOf(IllegalArgumentException.class);

        // git commit -m "feat(global): 시간 검증 기능 추가" -m "시간은 24시 표기가 아니고 분은 30분 단위가 아니면 IllegalArgumentException을 throw 하도록 구현하였습니다."
    }
}