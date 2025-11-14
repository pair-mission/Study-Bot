package repository;

import domain.Meeting;

import java.util.List;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    List<Meeting> findAll();

    Meeting findById(Long id);
}
