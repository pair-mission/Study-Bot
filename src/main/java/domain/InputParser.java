package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class InputParser {

    public static LocalDate parseDate(String input){
        return LocalDate.parse(input);
    }

    public static MeetingTime parseTime(String startTime, String endTime){
        return MeetingTime.of(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

}
