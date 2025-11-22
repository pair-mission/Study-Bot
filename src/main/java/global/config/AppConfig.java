package global.config;

import controller.*;
import global.Session;
import repository.attendance.AttendanceInMemoryRepository;
import repository.attendance.AttendanceRepository;
import repository.meeting.MeetingFileRepository;
import repository.meeting.MeetingRepository;
import repository.member.MemberFileRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantFileRepository;
import repository.participant.ParticipantRepository;
import service.AttendanceService;
import service.MeetingService;
import service.MemberService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class AppConfig {

    private static final AppConfig Instance = new AppConfig();

    // common
    private final Session session;
    private final InputView inputView;
    private final OutputView outputView;
    private final AuthHandler authHandler;
    private final RemindHandler remindHandler;

    // repository
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;
    private final ParticipantRepository participantRepository;

    // service
    private final MemberService memberService;
    private final AttendanceService attendanceService;
    private final MeetingService meetingService;

    // controller
    private final MemberController memberController;
    private final MeetingController meetingController;
    private final ExitController exitController;
    private final MainController mainController;

    private AppConfig() {

        session = new Session();
        inputView = new InputView();
        outputView = new OutputView();

        memberRepository = new MemberFileRepository();
        meetingRepository = new MeetingFileRepository();
        participantRepository = new ParticipantFileRepository(memberRepository, meetingRepository);
        attendanceRepository = new AttendanceInMemoryRepository();

        memberService = new MemberService(memberRepository);
        meetingService = new MeetingService(meetingRepository, participantRepository);
        attendanceService = new AttendanceService(attendanceRepository, participantRepository);

        memberController = new MemberController(memberService, inputView, outputView, session);
        meetingController = new MeetingController(meetingService, attendanceService, inputView, outputView, session);
        exitController = new ExitController(inputView, outputView, session, participantRepository, memberRepository, meetingRepository);
        authHandler = memberController;
        remindHandler = meetingController;

//        new DataInitializer().initializeMemory(memberRepository, meetingRepository, participantRepository);

        List<AppController> allControllers = List.of(
                memberController,
                meetingController,
                exitController
        );

        mainController = new MainController(inputView, outputView, allControllers, authHandler, remindHandler);
    }

    public static AppConfig getInstance() {
        return Instance;
    }

    public MainController getMainController() {
        return mainController;
    }

}
