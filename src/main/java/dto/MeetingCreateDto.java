package dto;

import global.enums.ErrorMessage;
import global.utils.InputValidator;
import global.utils.parser.InputParser;

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

    public static MeetingCreateDto from(String userInput) {

        List<String> tokens = InputParser.parseToTokens(userInput);

        if (tokens.size() != 5) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MEETING_REGISTER_INPUT.getMessage());
        }

        String date = tokens.get(0);
        String startTime = tokens.get(1);
        String endTime = tokens.get(2);
        String topic = InputParser.parseToNonBlank(tokens.get(3));
        String place = InputParser.parseToNonBlank(tokens.get(4));

        InputValidator.validateDate(date);
        InputValidator.validateTime(startTime);
        InputValidator.validateTime(endTime);

        return new MeetingCreateDto(date, startTime, endTime, topic, place);
    }
}
