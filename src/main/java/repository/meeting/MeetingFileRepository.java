package repository.meeting;

import domain.meeting.Meeting;
import global.enums.ErrorMessage;
import global.exception.DataAccessException;
import global.utils.CsvReader;
import global.utils.parser.MeetingParser;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            List<Meeting> meetings = CsvReader.readCsv(MEETING_FILE_PATH, new MeetingParser());
            return meetings;
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

    }
}
