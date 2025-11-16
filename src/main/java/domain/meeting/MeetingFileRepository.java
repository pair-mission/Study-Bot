package domain.meeting;

import global.ErrorMessage;
import global.exception.DataAccessException;
import java.io.IOException;
import java.util.List;
import untils.CsvReader;
import untils.MeetingParser;

public class MeetingFileRepository implements MeetingRepository {

    private static final String MEETING_FILE_PATH = "src/main/resources/meeting.csv";
    private Long sequence;

    public MeetingFileRepository() {
        try {
            List<Meeting> meetings = CsvReader.readCsv(MEETING_FILE_PATH, new MeetingParser());
            this.sequence = readNextSequence(meetings);

        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    private Long readNextSequence(List<Meeting> meetings) {
        return meetings.stream().mapToLong(Meeting::getId).max().orElse(-1L) + 1L;
    }

    @Override
    public void save(Meeting meeting) {
        try {
            meeting.setId(sequence);
            CsvReader.writeCsv(meeting, MEETING_FILE_PATH, new MeetingParser());
            ++sequence;
        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    @Override
    public List<Meeting> findAll() {
        try {
            List<Meeting> meetings = CsvReader.readCsv(MEETING_FILE_PATH, new MeetingParser());
            return meetings;
        } catch (IOException e) {
            throw new DataAccessException(ErrorMessage.INVALID_FILE);
        }
    }

    @Override
    public Meeting findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long meetingId) {

    }
}
