package domain.meeting;

import java.util.List;

public interface MeetingRepository {
    void save(Meeting meeting);

    List<Meeting> findAll();

    Meeting findById(Long id);
}
