package repository.attendance;

import domain.attendance.Attendance;
import domain.participant.MeetingParticipant;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {

    Attendance save(Attendance attendance);

    List<Attendance> findAll();

    Attendance findById(long id);

    List<Attendance> findAttendersByParticipants(List<MeetingParticipant> participants);

    Optional<Attendance> findByMeetingParticipant(MeetingParticipant participant);

}
