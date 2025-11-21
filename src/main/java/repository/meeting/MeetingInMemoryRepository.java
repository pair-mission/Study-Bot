package repository.meeting;

import domain.meeting.Meeting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MeetingInMemoryRepository implements MeetingRepository {
    private final Map<Long, Meeting> meetings = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Meeting save(Meeting meeting) {
        Meeting newMeeting = Meeting.of(sequence, meeting);
        meetings.put(sequence, newMeeting);
        sequence++;
        return newMeeting;
    }

    @Override
    public List<Meeting> findAll() {
        return new ArrayList<>(meetings.values());
    }

    @Override
    public Optional<Meeting> findById(Long id) {
        return Optional.ofNullable(meetings.get(id));

    }

    @Override
    public void delete(Long meetingId) {
        meetings.remove(meetingId);
    }
}
