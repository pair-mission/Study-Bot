package global.config;

import controller.AppController;
import controller.ExitController;
import controller.MainController;
import controller.MeetingController;
import controller.MemberController;
import global.Session;
import java.util.List;
import repository.attendance.AttendanceInMemoryRepository;
import repository.attendance.AttendanceRepository;
import repository.meeting.MeetingInMemoryRepository;
import repository.meeting.MeetingRepository;
import repository.member.MemberInMemoryRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantInMemoryRepository;
import service.AttendanceService;
import service.MeetingService;
import service.MemberService;
import view.InputView;
import view.OutputView;

public class AppConfig {

    private static final AppConfig Instance = new AppConfig();
    private final Session session = new Session();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final MeetingRepository meetingRepository = new MeetingInMemoryRepository();
    private final MemberRepository memberRepository = new MemberInMemoryRepository();
    private final AttendanceRepository attendanceRepository = new AttendanceInMemoryRepository();
    private final ParticipantInMemoryRepository participantRepository = new ParticipantInMemoryRepository();
    private final MemberService memberService = new MemberService(memberRepository);
    private final MemberController memberController = new MemberController(memberService, inputView, outputView,
            session);
    private final AttendanceService attendanceService = new AttendanceService(attendanceRepository,
            participantRepository);
    private final MeetingService meetingService = new MeetingService(meetingRepository, participantRepository);
    private final MeetingController meetingController = new MeetingController(meetingService, attendanceService,
            inputView,
            outputView, session);
    private final ExitController exitController = new ExitController(inputView, outputView, session);
    private final MainController mainController;

    private AppConfig() {
        new DataInitializer().initialize(memberRepository, meetingRepository, participantRepository);

        List<AppController> allControllers = List.of(
                memberController,
                meetingController,
                exitController
        );

        mainController = new MainController(inputView, outputView, allControllers);
    }

    public static AppConfig getInstance() {
        return Instance;
    }

    public MainController getMainController() {
        return mainController;
    }

    public MemberController getMemberController() {
        return memberController;
    }

    public MeetingController getMeetingController() {
        return meetingController;
    }

}
