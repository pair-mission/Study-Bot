package global.config;

import controller.AppController;
import controller.AuthHandler;
import controller.ControllerContext;
import controller.MainController;
import controller.RemindHandler;
import global.Session;
import java.util.List;
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

        AuthHandler authHandler = controllerConfig.getMemberController();
        RemindHandler remindHandler = controllerConfig.getMeetingController();

        List<AppController> allControllers = controllerConfig.getAllControllers();

        mainController = new MainController(inputView, outputView, allControllers, authHandler, remindHandler);
    }

    public static AppConfig getInstance() {
        return Instance;
    }

    public MainController getMainController() {
        return mainController;
    }
}
