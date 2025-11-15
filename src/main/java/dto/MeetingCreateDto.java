package dto;

import java.util.List;

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

    public static MeetingCreateDto from(List<String> tokens) {
        return new MeetingCreateDto(tokens.get(0), tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4));
    }
}
