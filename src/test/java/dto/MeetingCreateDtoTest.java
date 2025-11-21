package dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import global.enums.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MeetingCreateDtoTest {

    @ParameterizedTest
    @ValueSource(strings = {"15000", "wda:awd", "asda", "2:5", "21:!!"})
    @DisplayName("시간의 형식은 HH:mm 로 입력하지 않으면 예외가 발생한다.")
    void 시간의_형식을_규칙에_맞게_입력하지않으면_예외가_발생한다(String time) {

        String userInput = "2025-12-31, 19:00, " + time + ", 자바스터디, 강남역";

        assertThatThrownBy(() -> MeetingCreateDto.from(userInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_TIME_PATTERN.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"21:20", "18:22", "18:01", "00:01", "11:11"})
    @DisplayName("모임 시간의 분이 30분 단위가 아니면 예외가 발생한다")
    void 모임_시간의_분이_30분_단위가_아니면_예외가_발생한다(String time) {

        String userInput = "2025-12-31, 19:00, " + time + ", 자바스터디, 강남역";

        assertThatThrownBy(() -> MeetingCreateDto.from(userInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_TIME_PATTERN.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"25:00", "001:22"})
    @DisplayName("모임 시간의 시간은 24시 표기법이어야 한다")
    void 모임_시간의_시간은_24시_표기법이어야_한다(String time) {

        String userInput = "2025-12-31, 19:00, " + time + ", 자바스터디, 강남역";

        assertThatThrownBy(() -> MeetingCreateDto.from(userInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_TIME_PATTERN.getMessage());
    }

}