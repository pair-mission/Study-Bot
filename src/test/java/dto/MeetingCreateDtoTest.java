package dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MeetingCreateDtoTest {

    @ParameterizedTest
    @ValueSource(strings = {"2025:11:10", "2025.11.10", "2025-9-1"})
    @DisplayName("날짜의 형식을 \"YYYY-MM-dd\"로 입력하지 않으면 예외가 발생한다.")
    void validateDatePattern(String dateInput) {
        // given
        String startTime = "13:00";
        String endTime = "15:00";
        String topic = "topic";
        String place = "place";

        // when, then
        assertThatThrownBy(() -> MeetingCreateDto.of(dateInput, startTime, endTime, topic, place))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간의 형식을 \"HH:mm\"으로 입력하지 않으면 예외가 발생한다.")
    void testMeetingTimeFormat() {
        // given
        String date = LocalDate.now().toString();
        String startTime = "16-00";
        String endTime = "17-00";
        String topic = "topic";
        String place = "place";

        // when
        assertThatThrownBy(() -> MeetingCreateDto.of(date, startTime, endTime, topic, place))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간(Hour)는 24시 표기법이어야 한다.")
    void testMeetingTimeHour() {
        // given
        String date = LocalDate.now().toString();
        String startTime = "23-30";
        String endTime = "25-30";
        String topic = "topic";
        String place = "place";

        // when
        assertThatThrownBy(() -> MeetingCreateDto.of(date, startTime, endTime, topic, place))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("분(Minute)은 30분 단위여야 한다.")
    void testMeetingTimeMinute() {
        // given
        String date = LocalDate.now().toString();
        String startTime = "22:20";
        String endTime = "23:10";
        String topic = "topic";
        String place = "place";

        // when
        assertThatThrownBy(() -> MeetingCreateDto.of(date, startTime, endTime, topic, place))
                .isInstanceOf(IllegalArgumentException.class);
    }

}