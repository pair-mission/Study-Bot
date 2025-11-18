package controller;

import domain.meeting.Meeting;
import domain.member.Member;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import global.Session;
import global.utils.parser.InputParser;
import service.MeetingService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class MeetingController extends AppController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService, InputView inputView, OutputView outputView, Session session) {
        super(inputView, outputView, session);
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
        Member loginMember = session.getLoginMember();
        List<Meeting> meetings = meetingService.getMyMeetings(loginMember);
        outputView.printMyMeetings(meetings);
    }

    private void registerMeeting() {
        Member loginMember = session.getLoginMember();
        String userInput = inputView.getMeetingCreationInput();
        MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(userInput);

        meetingService.createMeeting(meetingCreateDto, loginMember);
        outputView.printMeetingRegisterSuccess();
    }

    private void updateMeeting() {
        Member loginMember = session.getLoginMember();
        List<Meeting> myMeetings = meetingService.getMyMeetings(loginMember);
        outputView.printMyMeetings(myMeetings);

        String userInput = inputView.getMeetingUpdateInput();
        MeetingUpdateDto meetingUpdateDto = MeetingUpdateDto.from(userInput);

        meetingService.updateMeeting(meetingUpdateDto, loginMember);
        outputView.printMeetingUpdateSuccess();
    }

    private void deleteMeeting() {
        Member loginMember = session.getLoginMember();
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
        Member loginMember = session.getLoginMember();
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
}
