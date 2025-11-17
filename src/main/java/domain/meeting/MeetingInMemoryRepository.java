package domain.meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static global.ErrorMessage.MEETING_NOT_FOUND;

public class MeetingInMemoryRepository implements MeetingRepository {
    private final Map<Long, Meeting> meetings = new HashMap<>();
    private Long sequence = 0L;

    public MeetingInMemoryRepository() {
        Meeting meeting1 = Meeting.of(0L, LocalDate.now().plusDays(1L),
                MeetingTime.of(LocalTime.now(), LocalTime.now().plusHours(2)), "자바 스터디", "스터디룸A");
        Meeting meeting2 = Meeting.of(1L, LocalDate.now().plusDays(1L),
                MeetingTime.of(LocalTime.now(), LocalTime.now().plusHours(2)), "코테", "디스코드");

        meetings.put(sequence++, meeting1);
        meetings.put(sequence++, meeting2);
    }

    @Override
    public void save(Meeting meeting) {
        meeting.setId(sequence);
        meetings.put(sequence, meeting);
        sequence++;
    }

    @Override
    public List<Meeting> findAll() {
        return new ArrayList<>(meetings.values());
    }

    @Override
    public Meeting findById(Long id) {
        return Optional.ofNullable(meetings.get(id))
                .orElseThrow(() -> new IllegalArgumentException(MEETING_NOT_FOUND.getMessage()));
    }

    @Override
    public void delete(Long meetingId) {
        meetings.remove(meetingId);
    }
}
