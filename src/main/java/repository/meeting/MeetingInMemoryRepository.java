package repository.meeting;

import domain.meeting.Meeting;

import java.util.*;

import static global.enums.ErrorMessage.MEETING_NOT_FOUND;

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
    public Meeting findById(Long id) {
        return Optional.ofNullable(meetings.get(id))
                .orElseThrow(() -> new IllegalArgumentException(MEETING_NOT_FOUND.getMessage()));
    }

    @Override
    public void delete(Long meetingId) {
        meetings.remove(meetingId);
    }
}
