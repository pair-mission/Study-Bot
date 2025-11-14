package dto;

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
}
