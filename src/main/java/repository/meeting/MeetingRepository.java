package repository.meeting;

import domain.meeting.Meeting;

import java.util.List;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    List<Meeting> findAll();

    Meeting findById(Long id);

    void delete(Long meetingId);
}
