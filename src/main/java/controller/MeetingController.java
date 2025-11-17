package controller;

import static global.ErrorMessage.INVALID_MENU_INPUT;

import domain.meeting.Meeting;
import domain.member.Member;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import dto.MemberInfoDto;
import global.InputValidator;
import global.exception.DataAccessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.MeetingService;
import service.MemberService;
import untils.InputParser;
import view.InputView;
import view.OutputView;

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
        while (true) {
            try {
                outputView.printMenu();
                login(inputView.getUserNickname());
                List<Meeting> meetings = meetingService.findByTomarrowMeetings(loginMember.getId());
                outputView.printIsTomorrowMeetings(meetings);
                int menuOption = getValidMenu();
                if (menuOption == 11) {
                    break;
                }
                handleOption(menuOption);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void registerAction() {
        actions.put(1, this::registerMeeting);
        actions.put(2, this::updateMeeting);
        actions.put(3, this::deleteMeeting);
        actions.put(4, this::showAllMeetings);
        actions.put(5, this::showAllMembers);
        actions.put(6, this::registerMember);
        actions.put(7, this::registerParticipant);
        actions.put(8, this::showParticipants);
        actions.put(10, this::showMyMeetings);
    }

    private void showMyMeetings() {
        List<Meeting> meetings = meetingService.getMyMeetings(loginMember);
        outputView.printMyMeetings(meetings);
    }

    private void registerMeeting() {
        String userInput = inputView.getMeetingCreationInput();
        List<String> tokens = InputParser.parseToTokens(userInput);
        MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(tokens);
        meetingService.createMeeting(meetingCreateDto, loginMember);
        outputView.printMeetingRegisterSuccess();
    }

    private void updateMeeting() {
        // TODO 내 모임 조회 먼저 보여줘야함
        String userInput = inputView.getMeetingUpdateInput();
        List<String> tokens = InputParser.parseToTokens(userInput);
        Long meetingId = Long.parseLong(tokens.getFirst());
        MeetingUpdateDto meetingUpdateDto = MeetingUpdateDto.from(tokens.get(1), tokens.get(2));
        meetingService.updateMeeting(loginMember, meetingId, meetingUpdateDto);
        outputView.printMeetingUpdateSuccess();
    }

    private void deleteMeeting() {
        String userInput = inputView.getMeetingDeleteInput();
        Long meetingId = Long.parseLong(userInput);
        meetingService.deleteMeeting(meetingId, loginMember);
        outputView.printMeetingDeleteMessage();
    }

    private void showAllMeetings() {
        List<MeetingInfoDto> allMeetings = meetingService.getAllMeetings();
        outputView.printAllMeetingInfo(allMeetings);
    }

    private void registerParticipant() {
        String meetingIdInput = inputView.getParticipantRegisterInput();
        long meetingId = InputParser.parseToLong(meetingIdInput);
        meetingService.createParticipant(meetingId, loginMember);
        outputView.printParticipantSuccess();
    }

    private void showParticipants() {
        String participantMemberInput = inputView.getParticipantMemberInput();
        long meetingId = InputParser.parseToLong(participantMemberInput);
        List<String> participantNicknames = meetingService.getAllParticipants(meetingId);
        outputView.printAllParticipants(participantNicknames);
    }

    private void registerMember() {
        try {
            String userInput = inputView.getMemberNickname();
            String newUserInput = InputParser.parseToValidString(userInput);
            Member member = memberService.register(newUserInput);
            outputView.printRegisterSuccess(member.getNickname());
        } catch (DataAccessException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void showAllMembers() {
        try {
            List<MemberInfoDto> memberInfos = memberService.findAllMember().stream().map(MemberInfoDto::from).toList();
            outputView.printAllMemberInfo(memberInfos);
        } catch (DataAccessException e) {
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
        if (menu != 6) {
            login(inputView.getUserNickname());
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
