package global.utils.parser;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;

public class MeetingParser implements CsvParser<Meeting> {

    @Override
    public Meeting parse(String[] csv) {
        Long id = Long.parseLong(csv[0]);
        LocalDate date = InputParser.parseToDate(csv[1]);
        MeetingTime meetingTime = InputParser.parseToTime(csv[2], csv[3]);
        String topic = csv[4];
        String place = csv[5];

        return Meeting.of(id, date, meetingTime, topic, place);
    }

    @Override
    public void write(Meeting meeting, BufferedWriter br, boolean isEmptyFile) throws IOException {

        if (isEmptyFile) {
            br.write("id,date,startTime,endTime,topic,place");
        }

        br.write("\n" + meeting.getId() + "," +
                meeting.getDate() + "," +
                meeting.getMeetingTime().getStartTime() + "," +
                meeting.getMeetingTime().getEndTime() + "," +
                meeting.getTopic() + "," +
                meeting.getPlace()

        );

        br.flush();

    }

    @Override
    public void update(Meeting meeting, BufferedWriter writer, String[] csv) {

    }
}
