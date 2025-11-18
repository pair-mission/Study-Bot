import controller.MainController;
import global.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = AppConfig.getInstance();
        MainController mainController = appConfig.getMainController();
        mainController.run();
    }
}