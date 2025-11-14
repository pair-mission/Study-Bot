package dto;

public record MeetingUpdateDto(
        String topic,
        String place
) {
    public static MeetingUpdateDto from(String newTopic, String newPlace) {
        return new MeetingUpdateDto(newTopic, newPlace);
    }
}
