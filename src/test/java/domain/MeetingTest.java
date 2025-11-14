package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MeetingTest {

    @Test
    @DisplayName("모임의 날짜, 시작 및 종료 시간, 주제, 장소 중 정보가 하나라도 없으면 예외가 발생한다.")
    void testMeetingDate() {
        // given
        LocalDate date = LocalDate.now().minusDays(10);
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = LocalTime.now().plusHours(2);
        String topic = "study topic";
        String place = "study place";

        // when
        assertThatThrownBy(() ->
                Meeting.of(date, MeetingTime.of(startTime, endTime), topic, place)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}