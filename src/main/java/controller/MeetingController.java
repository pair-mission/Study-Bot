package controller;

import static global.ErrorMessage.INVALID_MENU_INPUT;

import domain.meeting.Meeting;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.MeetingService;
import untils.InputParser;
import view.InputView;
import view.OutputView;

public class MeetingController implements AppController {

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
        actions.put(2, this::updateMeeting);
        actions.put(3, this::deleteMeeting);
        actions.put(4, this::showAllMeetings);
        actions.put(7, this::registerParticipant);
        actions.put(8, this::showParticipants);
        actions.put(10, this::showMyMeetings);
    }

    @Override
    public void controlAction(int option) {
        handleOption(option);
    }

    @Override
    public void handleOption(int option) {
        Runnable action = actions.get(option);
        if (action == null) {
            outputView.printErrorMessage(INVALID_MENU_INPUT.getMessage());
            return;
        }
        action.run();
    }

    private void showMyMeetings() {
        String userNickname = inputView.getUserNickname();
        List<Meeting> meetings = meetingService.getMyMeetings(userNickname);
        outputView.printMyMeetings(meetings);
    }

    private void registerMeeting() {
        String userNickname = inputView.getUserNickname();
        String userInput = inputView.getMeetingCreationInput();

        MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(userInput);

        meetingService.createMeeting(meetingCreateDto, userNickname);
        outputView.printMeetingRegisterSuccess();
    }

    private void updateMeeting() {
        // TODO 내 모임 조회 먼저 보여줘야함
        String userNickname = inputView.getUserNickname();
        String userInput = inputView.getMeetingUpdateInput();

        MeetingUpdateDto meetingUpdateDto = MeetingUpdateDto.from(userInput);

        meetingService.updateMeeting(userNickname, meetingUpdateDto);
        outputView.printMeetingUpdateSuccess();
    }

    private void deleteMeeting() {
        String userNickname = inputView.getUserNickname();
        String userInput = inputView.getMeetingDeleteInput();
        Long meetingId = Long.parseLong(userInput);

        meetingService.deleteMeeting(meetingId, userNickname);
        outputView.printMeetingDeleteMessage();
    }

    private void showAllMeetings() {
        List<MeetingInfoDto> allMeetings = meetingService.getAllMeetings();
        outputView.printAllMeetingInfo(allMeetings);
    }

    private void registerParticipant() {
        String userNickname = inputView.getUserNickname();
        String meetingIdInput = inputView.getParticipantRegisterInput();
        long meetingId = InputParser.parseToLong(meetingIdInput);

        meetingService.createParticipant(meetingId, userNickname);
        outputView.printParticipantSuccess();
    }

    private void showParticipants() {
        String participantMemberInput = inputView.getParticipantMemberInput();
        long meetingId = InputParser.parseToLong(participantMemberInput);
        List<String> participantNicknames = meetingService.getAllParticipants(meetingId);
        outputView.printAllParticipants(participantNicknames);
    }
}
