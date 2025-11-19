package repository.attendance;

import domain.attendance.Attendance;
import java.util.List;

public interface AttendanceRepository {

    Attendance save(Attendance attendance);

    List<Attendance> findAll();

    Attendance findById(long id);


}
