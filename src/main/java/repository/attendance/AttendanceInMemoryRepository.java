package repository.attendance;

import domain.attendance.Attendance;
import domain.participant.MeetingParticipant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceInMemoryRepository implements AttendanceRepository {

    Map<Long, Attendance> attendances = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Attendance save(Attendance attendance) {

        Attendance newAttendance = Attendance.of(sequence, attendance);
        attendances.put(sequence, newAttendance);
        sequence++;

        return newAttendance;
    }

    @Override
    public List<Attendance> findAll() {
        return List.of();
    }

    @Override
    public Attendance findById(long id) {
        return null;
    }

    @Override
    public List<Attendance> findAttendersByParticipants(List<MeetingParticipant> participants) {
        return attendances.values().stream()
                .filter(attendance -> participants.contains(attendance.getParticipant()))
                .collect(Collectors.toList());
    }

}

