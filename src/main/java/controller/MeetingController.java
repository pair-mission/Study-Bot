package controller;

import domain.member.Member;
import dto.MeetingCreateDto;
import dto.MemberInfoDto;
import global.InputValidator;
import service.MeetingService;
import service.MemberService;
import untils.InputParser;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static global.ErrorMessage.INVALID_MENU_INPUT;

public class MeetingController {

    private final MemberService memberService;
    private final MeetingService meetingService;
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Integer, Runnable> actions;
    private Member loginMember;

    public MeetingController(MemberService memberService, MeetingService meetingService,
                             InputView inputView, OutputView outputView) {
        this.memberService = memberService;
        this.meetingService = meetingService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.actions = new HashMap<>();
        registerAction();
    }

    public void start() {
        try {
            outputView.printMenu();
            login(inputView.getUserNickname());
            int menuOption = getValidMenu();
            handleOption(menuOption);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void registerAction() {
        actions.put(1, this::registerMeeting);
//        actions.put(2, createMeetings());
//        actions.put(3, createMeetings());
//        actions.put(4, createMeetings());
        actions.put(5, this::showAllMembers);
//        actions.put(6, createMeetings());
//        actions.put(7, createMeetings());
    }

    private void registerMeeting() {
        String userInput = inputView.getMeetingCreationInput();
        List<String> tokens = InputParser.parseToTokens(userInput);
        MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(tokens);
//        meetingService.createMeeting(meetingCreateDto, );
    }

    private void showAllMembers() {
        try {
            List<MemberInfoDto> memberInfos = memberService.findAllMember().stream().map(MemberInfoDto::from).toList();
            outputView.printAllMemberInfo(memberInfos);
        } catch (IOException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void login(String nickname) {
        InputValidator.validateBlankInput(nickname);
        String trimmedNickname = InputParser.parseToValidString(nickname);
        loginMember = memberService.findByNickName(trimmedNickname);
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
