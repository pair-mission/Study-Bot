package global.config;

import controller.AppController;
import controller.AuthHandler;
import controller.ControllerContext;
import controller.MainController;
import controller.RemindHandler;
import external.DiscordClient;
import global.Session;
import java.net.http.HttpClient;
import java.util.List;
import schedule.RemindJob;
import schedule.ReminderScheduler;
import service.MeetingService;
import view.InputView;
import view.OutputView;

public class AppConfig {

    private static final AppConfig Instance = new AppConfig();

    private final MainController mainController;

    private AppConfig() {

        // common
        Session session = new Session();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ControllerContext controllerContext = new ControllerContext(inputView, outputView, session);

        RepositoryConfig repositoryConfig = new RepositoryConfig();
        ServiceConfig serviceConfig = new ServiceConfig(repositoryConfig);
        ControllerConfig controllerConfig = new ControllerConfig(serviceConfig, controllerContext, repositoryConfig);

        AuthHandler authHandler = controllerConfig.getAuthHandler();
        RemindHandler remindHandler = controllerConfig.getRemindHandler();

        List<AppController> allControllers = controllerConfig.getAllControllers();

        MeetingService meetingService = serviceConfig.getMeetingService();

        String webhookUrl = System.getenv("DISCORD_WEBHOOK_URL");
        DiscordClient discordClient = new DiscordClient(HttpClient.newHttpClient(), webhookUrl);
        RemindJob remindJob = new RemindJob(meetingService, discordClient);
        ReminderScheduler reminderScheduler = new ReminderScheduler(remindJob);

        reminderScheduler.startDailyAt(21, 0);
        mainController = new MainController(inputView, outputView, allControllers, authHandler, remindHandler);
    }

    public static AppConfig getInstance() {
        return Instance;
    }

    public MainController getMainController() {
        return mainController;
    }
}
