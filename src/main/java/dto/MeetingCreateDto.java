package dto;

import java.util.List;
import untils.InputParser;

public record MeetingCreateDto(
        String date,
        String startTime,
        String endTime,
        String topic,
        String place
) {
    public static MeetingCreateDto of(String date, String startTime, String endTime, String topic, String place) {
        return new MeetingCreateDto(date, startTime, endTime, topic, place);
    }

    public static MeetingCreateDto from(String userInput) {
        
        List<String> tokens = InputParser.parseToTokens(userInput);
        String date = tokens.get(0);
        String startTime = tokens.get(1);
        String endTime = tokens.get(2);
        String topic = tokens.get(3);
        String place = tokens.get(4);
        
        return new MeetingCreateDto(date, startTime, endTime, topic, place);
    }
}
