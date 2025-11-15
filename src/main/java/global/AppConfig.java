package global;

import controller.MeetingController;
import domain.meeting.MeetingInMemoryRepository;
import domain.meeting.MeetingRepository;
import domain.meeting.ParticipantInMemoryRepository;
import domain.member.MemberInMemoryRepository;
import domain.member.MemberRepository;
import service.MeetingService;
import view.InputView;
import view.OutputView;

public class AppConfig {
    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public MeetingRepository meetingRepository() {
        return new MeetingInMemoryRepository();
    }

    public MemberRepository memberRepository() {
        return new MemberInMemoryRepository();
    }

    public ParticipantInMemoryRepository participantRepository() {
        return new ParticipantInMemoryRepository();
    }

    public MeetingService meetingService() {
        return new MeetingService(meetingRepository(), memberRepository(), participantRepository());
    }

    public MeetingController meetingController() {
        return new MeetingController(meetingService(), inputView(), outputView());
    }

}
