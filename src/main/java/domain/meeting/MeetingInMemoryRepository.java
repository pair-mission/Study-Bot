package domain.meeting;

import static global.ErrorMessage.MEETING_NOT_FOUND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MeetingInMemoryRepository implements MeetingRepository {
    private final Map<Long, Meeting> meetings = new HashMap<>();
    private Long sequence = 0L;

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

}
