package global;

import controller.MeetingController;
import domain.meeting.MeetingInMemoryRepository;
import domain.meeting.MeetingRepository;
import domain.meeting.ParticipantInMemoryRepository;
import domain.member.MemberInMemoryRepository;
import domain.member.MemberRepository;
import service.MeetingService;
import service.MemberService;
import view.InputView;
import view.OutputView;

public class AppConfig {

    private static final AppConfig Instance = new AppConfig();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final MeetingRepository meetingRepository = new MeetingInMemoryRepository();
    private final MemberRepository memberRepository = new MemberInMemoryRepository();
    private final ParticipantInMemoryRepository participantRepository = new ParticipantInMemoryRepository();
    private final MemberService memberService = new MemberService(memberRepository);
    private final MeetingService meetingService = new MeetingService(meetingRepository, memberRepository,
            participantRepository);
    private final MeetingController meetingController = new MeetingController(memberService, meetingService, inputView,
            outputView);

    private AppConfig() {
        new DataInitializer().initialize(memberRepository, meetingRepository, participantRepository);
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

    public MeetingRepository meetingRepository() {
        return meetingRepository;
    }

    public MemberRepository memberRepository() {
        return memberRepository;
    }

    public ParticipantInMemoryRepository participantRepository() {
        return participantRepository;
    }

    public MemberService memberService() {
        return memberService;
    }

    public MeetingService meetingService() {
        return meetingService;
    }

    public MeetingController meetingController() {
        return meetingController;
    }

}
