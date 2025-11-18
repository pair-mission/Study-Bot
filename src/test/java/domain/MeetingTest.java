package domain;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import global.enums.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MeetingTest {

    private static Stream<Arguments> provideRacingCars() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(9, 0);

        return Stream.of(
                Arguments.of(null, MeetingTime.of(startTime, endTime), "topic", "place"),
                Arguments.of(LocalDate.now(), null, "topic", "place"),
                Arguments.of(LocalDate.now(), MeetingTime.of(startTime, endTime), null, "place"),
                Arguments.of(LocalDate.now(), MeetingTime.of(startTime, endTime), "topic", null)
        );
    }

    @DisplayName("모임의 날짜, 시작 및 종료 시간, 주제, 장소 중 정보가 하나라도 빈값이거나 null이면 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("provideRacingCars")
    void testMeetingInfo(LocalDate date, MeetingTime meetingTime, String topic, String place) {
        assertThatThrownBy(() -> Meeting.of(date, meetingTime, topic, place))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.NULL_OR_EMPTY.getMessage());
    }

    @Test
    @DisplayName("모임의 날짜가 현재 기준으로 과거인 경우 예외가 발생한다.")
    void testMeetingTimes() {

        LocalDate date = LocalDate.of(2025, 8, 15);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(9, 0);
        String topic = "topic";
        String place = "place";

        assertThatThrownBy(() -> Meeting.of(date, MeetingTime.of(startTime, endTime), topic, place))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_DATE.getMessage());
    }

}