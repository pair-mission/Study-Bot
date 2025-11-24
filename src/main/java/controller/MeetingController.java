package controller;

import domain.meeting.Meeting;
import domain.member.Member;
import dto.MeetingAttendanceDto;
import dto.MeetingCreateDto;
import dto.MeetingInfoDto;
import dto.MeetingUpdateDto;
import global.enums.MainMenu;
import global.utils.parser.InputParser;
import service.AttendanceService;
import service.MeetingService;

import java.util.List;

public class MeetingController extends AppController implements RemindHandler {

    private final MeetingService meetingService;
    private final AttendanceService attendanceService;

    public MeetingController(MeetingService meetingService, AttendanceService attendanceService,
                             ControllerContext context) {
        super(context);
        this.meetingService = meetingService;
        this.attendanceService = attendanceService;
    }

    @Override
    protected void registerAction() {
        actions.put(MainMenu.MEETING_REGISTER, this::registerMeeting);
        actions.put(MainMenu.MEETING_UPDATE, this::updateMeeting);
        actions.put(MainMenu.MEETING_DELETE, this::deleteMeeting);
        actions.put(MainMenu.MEETING_LIST, this::showAllMeetings);
        actions.put(MainMenu.PARTICIPANT_LIST, this::showParticipants);
        actions.put(MainMenu.PARTICIPANT_REGISTER, this::registerParticipant);
        actions.put(MainMenu.MY_MEETING_LIST, this::showMyMeetings);
        actions.put(MainMenu.ATTENDANCE_CHECK, this::registerAttendance);
        actions.put(MainMenu.ATTENDANCE_HISTORY, this::showAttendanceHistory);
        actions.put(MainMenu.MY_NEXT_MEETING, this::showMyNextMeetings);
    }

    @Override
    public void showRemindMeetings() {
        Member loginMember = session.getLoginMember();
        List<Meeting> remindMeetings = meetingService.findRemindMeetings(loginMember.getId(),
                loginMember.getRemindDay());
        outputView.printRemindMeetings(remindMeetings);
    }

    private void showMyNextMeetings() {
        Member loginMember = session.getLoginMember();
        Meeting myNextMeeting = meetingService.getMyNextMeeting(loginMember.getId());
        outputView.printMyNextMeeting(myNextMeeting);
    }

    private void registerAttendance() {
        Member loginMember = session.getLoginMember();

        List<Meeting> myMeetings = meetingService.getMyMeetings(loginMember);
        outputView.printMyMeetings(myMeetings);

        String meetingInput = inputView.getAttendanceInput();
        Long meetingId = InputParser.parseToLong(meetingInput);

        try {
            attendanceService.createAttendance(meetingId, loginMember);
            outputView.printAttendanceSuccess();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void showAttendanceHistory() {
        List<Meeting> allMeetings = meetingService.findAllMeetings();
        List<MeetingAttendanceDto> attendanceHistory = attendanceService.findAllAttendance(allMeetings);
        outputView.printAttendanceHistory(attendanceHistory);
    }

    private void showMyMeetings() {
        Member loginMember = session.getLoginMember();
        List<Meeting> meetings = meetingService.getMyMeetings(loginMember);
        outputView.printMyMeetings(meetings);
    }

    private void registerMeeting() {
        try {
            Member loginMember = session.getLoginMember();
            String userInput = inputView.getMeetingCreationInput();
            MeetingCreateDto meetingCreateDto = MeetingCreateDto.from(userInput);

            meetingService.createMeeting(meetingCreateDto, loginMember);
            outputView.printMeetingRegisterSuccess();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
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
