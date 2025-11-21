package repository.meeting;

import domain.meeting.Meeting;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    List<Meeting> findAll();

    Optional<Meeting> findById(Long id);

    void delete(Long meetingId);
}
