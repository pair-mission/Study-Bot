import controller.AppController;
import global.AppConfig;
import global.Menu;
import untils.InputParser;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = AppConfig.getInstance();
        OutputView outputView = appConfig.outputView();
        InputView inputView = appConfig.inputView();

        Menu menu;
        do {
            outputView.printMenu();
            menu = Menu.findByOption(InputParser.parseToInt(inputView.getMenuInput()));
            AppController controller = appConfig.getController(menu);
            controller.controlAction(menu.getOption());
        } while (menu.isNotExit());
    }

}