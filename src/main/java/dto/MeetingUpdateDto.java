package dto;

import java.util.Optional;

public record MeetingUpdateDto(
        Optional<String> topic,
        Optional<String> place
) {
    public static MeetingUpdateDto from(String newTopic, String newPlace) {
        return new MeetingUpdateDto(Optional.of(newTopic), Optional.of(newPlace));
    }
}
