package controller;

import static global.ErrorMessage.INVALID_MENU_INPUT;

import dto.MeetingCreateDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.MeetingService;
import untils.InputParser;
import view.InputView;
import view.OutputView;

public class MeetingController {

    private final MeetingService meetingService;
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Integer, Runnable> actions;

    public MeetingController(MeetingService meetingService, InputView inputView, OutputView outputView) {
        this.meetingService = meetingService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.actions = new HashMap<>();
        registerAction();
    }

    private void registerAction() {
        actions.put(1, this::registerMeeting);
//        actions.put(2, createMeetings());
//        actions.put(3, createMeetings());
//        actions.put(4, createMeetings());
//        actions.put(5, createMeetings());
//        actions.put(6, createMeetings());
//        actions.put(7, createMeetings());
    }

    private void registerMeeting() {
        String userInput = inputView.getMeetingCreationInput();
        List<String> tokens = InputParser.parseTokens(userInput);
        MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(tokens);
//        meetingService.createMeeting(meetingCreateDto, );
    }

    public void start() {
        try {
            outputView.printMenu();
            String nickname = inputView.getUserNickname();
//            login();
            int menuOption = getValidMenu();
            handleOption(menuOption);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void login() {

    }

    private void handleOption(int menu) {
        Runnable action = actions.get(menu);
        if (action == null) {
            outputView.printErrorMessage(INVALID_MENU_INPUT.getMessage());
            return;
        }
        action.run();
    }

    private Integer getValidMenu() {
        while (true) {
            try {
                String menu = inputView.getMenuInput();
                return Integer.parseInt(menu);
            } catch (NumberFormatException e) {
                outputView.printErrorMessage(INVALID_MENU_INPUT.getMessage());
            }
        }
    }

}
