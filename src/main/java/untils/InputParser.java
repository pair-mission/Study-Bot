package untils;

import domain.meeting.MeetingTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class InputParser {

    public static LocalDate parseDate(String input) {
        return LocalDate.parse(input);
    }

    public static MeetingTime parseTime(String startTime, String endTime) {
        return MeetingTime.of(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public static List<String> parseTokens(String input) {
        return Arrays.stream(input.split(",")).map(String::trim).toList();
    }

    public static String parseValidString(String input) {
        return input.replaceAll("\\s+", "");
    }

}
