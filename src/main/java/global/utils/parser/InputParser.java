package global.utils.parser;

import domain.meeting.MeetingTime;
import global.enums.ErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class InputParser {

    public static LocalDate parseToDate(String input) {
        return LocalDate.parse(input);
    }

    public static MeetingTime parseToTime(String startTime, String endTime) {
        return MeetingTime.of(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public static List<String> parseToTokens(String input) {
        return Arrays.stream(input.split(",")).map(String::trim).toList();
    }

    public static String parseToValidString(String input) {
        return input.replaceAll("\\s+", "");
    }

    public static long parseToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    public static int parseToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

}
