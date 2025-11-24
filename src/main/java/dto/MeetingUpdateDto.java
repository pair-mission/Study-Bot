package dto;

import global.enums.ErrorMessage;
import global.utils.parser.InputParser;

import java.util.List;
import java.util.Optional;

public record MeetingUpdateDto(
        Long meetingId,
        Optional<String> topic,
        Optional<String> place
) {
    public static MeetingUpdateDto from(String userInput) {

        List<String> tokens = InputParser.parseToTokens(userInput);

        if (tokens.size() != 3) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MEETING_UPDATE_INPUT.getMessage());
        }

        Long meetingId = InputParser.parseToLong(tokens.get(0));
        String topic = tokens.get(1);
        String place = tokens.get(2);

        return new MeetingUpdateDto(meetingId, Optional.of(topic), Optional.of(place));
    }
}
