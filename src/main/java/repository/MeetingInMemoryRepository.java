package repository;

import domain.Meeting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingInMemoryRepository implements MeetingRepository {

    private Map<Long, Meeting> meetings = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Meeting save(Meeting meeting) {

        meetings.put(sequence++, meeting);
        return meeting;
    }

    @Override
    public List<Meeting> findAll() {
        return new ArrayList<>(meetings.values());
    }
}
