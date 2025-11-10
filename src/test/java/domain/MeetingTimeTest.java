package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MeetingTimeTest {

    @Test
    @DisplayName("종료 시간이 시작시간보다 빠른 경우 예외가 발생한다.")
    void testMeetingTime() {
        // given
        LocalTime startTime = LocalTime.of(12, 30, 0);
        LocalTime endTime = LocalTime.of(12, 10, 0);
        // 명시를 꼭 하자 ! 하루 전으로 돌아가면 시간을 더 늦은 시간이 된다..

        // when
        assertThatThrownBy(() -> MeetingTime.of(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
