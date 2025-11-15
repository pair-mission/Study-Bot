package domain.meeting;

import java.util.List;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    List<Meeting> findAll();

    Meeting findById(Long id);
}
