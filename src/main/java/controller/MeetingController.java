package controller;

import domain.meeting.Meeting;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import global.utils.parser.InputParser;
import java.util.List;
import service.MeetingService;
import view.InputView;
import view.OutputView;

public class MeetingController extends AppController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService, InputView inputView, OutputView outputView) {
        super(inputView, outputView);
        this.meetingService = meetingService;
    }

    @Override
    protected void registerAction() {
        actions.put(1, this::registerMeeting);
        actions.put(2, this::updateMeeting);
        actions.put(3, this::deleteMeeting);
        actions.put(4, this::showAllMeetings);
        actions.put(7, this::registerParticipant);
        actions.put(8, this::showParticipants);
        actions.put(10, this::showMyMeetings);
    }

    private void showMyMeetings() {
        String userNickname = inputView.getUserNickname();
        List<Meeting> meetings = meetingService.getMyMeetings(userNickname);
        outputView.printMyMeetings(meetings);
    }

    private void registerMeeting() {
        String userInput = inputView.getMeetingCreationInput();

        MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(userInput);

        meetingService.createMeeting(meetingCreateDto, loginMember.getNickname());
        outputView.printMeetingRegisterSuccess();
    }

    private void updateMeeting() {
        // TODO 내 모임 조회 먼저 보여줘야함
        String userInput = inputView.getMeetingUpdateInput();

        MeetingUpdateDto meetingUpdateDto = MeetingUpdateDto.from(userInput);

        meetingService.updateMeeting(loginMember.getNickname(), meetingUpdateDto);
        outputView.printMeetingUpdateSuccess();
    }

    private void deleteMeeting() {
        String userInput = inputView.getMeetingDeleteInput();
        Long meetingId = Long.parseLong(userInput);

        meetingService.deleteMeeting(meetingId, loginMember.getNickname());
        outputView.printMeetingDeleteMessage();
    }

    private void showAllMeetings() {
        List<MeetingInfoDto> allMeetings = meetingService.getAllMeetings();
        outputView.printAllMeetingInfo(allMeetings);
    }

    private void registerParticipant() {
        String meetingIdInput = inputView.getParticipantRegisterInput();
        long meetingId = InputParser.parseToLong(meetingIdInput);

        meetingService.createParticipant(meetingId, loginMember.getNickname());
        outputView.printParticipantSuccess();
    }

    private void showParticipants() {
        String participantMemberInput = inputView.getParticipantMemberInput();
        long meetingId = InputParser.parseToLong(participantMemberInput);
        List<String> participantNicknames = meetingService.getAllParticipants(meetingId);
        outputView.printAllParticipants(participantNicknames);
    }
}
