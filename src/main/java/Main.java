import controller.MeetingController;
import global.AppConfig;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MeetingController controller = appConfig.meetingController();
        controller.start();
    }
}