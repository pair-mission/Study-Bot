package repository.meeting;

import domain.meeting.Meeting;
import global.enums.ErrorMessage;
import global.exception.DataAccessException;
import global.utils.CsvReader;
import global.utils.parser.MeetingParser;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MeetingFileRepository implements MeetingRepository {

    public static final String MEETING_FILE_PATH = "src/main/resources/meeting.csv";
    private final Map<Long, Meeting> meetings = new HashMap<>();
    private Long sequence;

    public MeetingFileRepository() {
        try {
            List<Meeting> csvMeetings = CsvReader.readCsv(MEETING_FILE_PATH, new MeetingParser());

            for (Meeting meeting : csvMeetings) {
                this.meetings.put(meeting.getId(), meeting);
            }

            this.sequence = readNextSequence(csvMeetings);
        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    private Long readNextSequence(List<Meeting> meetings) {
        return meetings.stream().mapToLong(Meeting::getId).max().orElse(-1L) + 1L;
    }

    @Override
    public Meeting save(Meeting meeting) {
        try {
            Meeting newMeeting = Meeting.of(sequence++, meeting);
            CsvReader.writeCsv(newMeeting, MEETING_FILE_PATH, new MeetingParser());
            return newMeeting;
        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    @Override
    public List<Meeting> findAll() {
        try {
            return CsvReader.readCsv(MEETING_FILE_PATH, new MeetingParser());
        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    @Override
    public Optional<Meeting> findById(Long id) {
        try {
            return CsvReader.readCsv(MEETING_FILE_PATH, new MeetingParser()).stream()
                    .filter(meeting -> meeting.isSameById(id))
                    .findFirst();
        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    @Override
    public void delete(Long meetingId) {
        meetings.remove(meetingId);
    }

    @Override
    public List<Meeting> findByDate(LocalDate date) {
        return meetings.values().stream().filter(meeting -> meeting.getDate().equals(date)).toList();
    }
}
