package global.config;

import controller.AppController;
import controller.AuthHandler;
import controller.ControllerContext;
import controller.ExitController;
import controller.MainController;
import controller.MeetingController;
import controller.MemberController;
import controller.RemindHandler;
import global.Session;
import java.util.List;
import repository.attendance.AttendanceInMemoryRepository;
import repository.attendance.AttendanceRepository;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantRepository;
import service.AttendanceService;
import service.MeetingService;
import service.MemberService;
import view.InputView;
import view.OutputView;

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
    private final ControllerContext controllerContext;

    private AppConfig() {

        session = new Session();
        inputView = new InputView();
        outputView = new OutputView();
        controllerContext = new ControllerContext(inputView, outputView, session);

        RepositoryConfig repositoryConfig = new RepositoryConfig();
        meetingRepository = repositoryConfig.getMeetingRepository();
        memberRepository = repositoryConfig.getMemberRepository();
        participantRepository = repositoryConfig.getParticipantRepository();
        attendanceRepository = new AttendanceInMemoryRepository();

        memberService = new MemberService(memberRepository);
        meetingService = new MeetingService(meetingRepository, participantRepository);
        attendanceService = new AttendanceService(attendanceRepository, participantRepository);

        memberController = new MemberController(memberService, controllerContext);
        meetingController = new MeetingController(meetingService, attendanceService, controllerContext);
        exitController = new ExitController(controllerContext, participantRepository, memberRepository,
                meetingRepository);
        authHandler = memberController;
        remindHandler = meetingController;

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
