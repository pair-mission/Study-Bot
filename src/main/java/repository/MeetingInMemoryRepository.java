package repository;

import domain.Meeting;

import java.util.*;

import static global.ErrorMessage.MEETING_NOT_FOUND;

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

    @Override
    public Meeting findById(Long id) {
        return Optional.ofNullable(meetings.get(id))
                .orElseThrow(() -> new IllegalArgumentException(MEETING_NOT_FOUND.getMessage()));
    }

}
