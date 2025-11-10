import domain.Meeting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MeetingTest {

    @Test
    @DisplayName("스터디 모임의 날짜, 시작 및 종료 시간, 주제, 장소를 입력받아 모임을 등록한다.")
    void testMeetingCreation() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(2);
        String topic = "study topic";
        String place = "study place";

        // when
        Meeting meeting = Meeting.of(date, startTime, endTime, topic, place);

        // then
        assertNotNull(meeting);
        assertThat(meeting.getDate()).isEqualTo(date.toString());
        assertThat(meeting.getMeetingTime().getStartTime()).isEqualTo(startTime);
        assertThat(meeting.getMeetingTime().getEndTime()).isEqualTo(endTime);
        assertThat(meeting.getTopic()).isEqualTo(topic);
        assertThat(meeting.getPlace()).isEqualTo(place);
    }

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
                Meeting.of(date, startTime, endTime, topic, place)
        ).isInstanceOf(IllegalArgumentException.class);
    }

}