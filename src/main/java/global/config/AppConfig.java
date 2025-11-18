package global.config;

import controller.*;
import global.Session;
import global.enums.Menu;
import repository.meeting.MeetingInMemoryRepository;
import repository.meeting.MeetingRepository;
import repository.member.MemberInMemoryRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantInMemoryRepository;
import service.MeetingService;
import service.MemberService;
import view.InputView;
import view.OutputView;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {

    private static final AppConfig Instance = new AppConfig();
    private final Session session = new Session();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final MeetingRepository meetingRepository = new MeetingInMemoryRepository();
    private final MemberRepository memberRepository = new MemberInMemoryRepository();
    private final ParticipantInMemoryRepository participantRepository = new ParticipantInMemoryRepository();
    private final MemberService memberService = new MemberService(memberRepository);
    private final MemberController memberController = new MemberController(memberService, inputView, outputView, session);
    private final MeetingService meetingService = new MeetingService(meetingRepository, participantRepository);
    private final MeetingController meetingController = new MeetingController(meetingService, inputView,
            outputView, session);
    private final ExitController exitController = new ExitController(inputView, outputView, session);
    private final Map<Menu, AppController> controllers = new HashMap<>();
    private final MainController mainController = new MainController(inputView, outputView, controllers);

    private AppConfig() {
        new DataInitializer().initialize(memberRepository, meetingRepository, participantRepository);
        controllers.put(Menu.MEETING_REGISTER, meetingController);
        controllers.put(Menu.MEETING_UPDATE, meetingController);
        controllers.put(Menu.MEETING_DELETE, meetingController);
        controllers.put(Menu.MEETING_LIST, meetingController);
        controllers.put(Menu.MEMBER_REGISTER, memberController);
        controllers.put(Menu.MEMBER_LIST, memberController);
        controllers.put(Menu.PARTICIPANT_REGISTER, meetingController);
        controllers.put(Menu.PARTICIPANT_LIST, meetingController);
        controllers.put(Menu.MY_MEETING_LIST, meetingController);
        controllers.put(Menu.EXIT, exitController);
        controllers.put(Menu.LOGIN, memberController);
    }

    public static AppConfig getInstance() {
        return Instance;
    }

    public InputView inputView() {
        return inputView;
    }

    public OutputView outputView() {
        return outputView;
    }

    public AppController getController(Menu option) {
        return controllers.get(option);
    }

    public MainController getMainController() {
        return mainController;
    }

}
