package controller;

import global.enums.Menu;
import global.utils.parser.InputParser;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class MainController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Menu, AppController> controllers;

    public MainController(InputView inputView, OutputView outputView, Map<Menu, AppController> controllers) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controllers = controllers;
    }

    public void run() {

        processLoginOrRegister();

        Menu menu;
        do {
            outputView.printMenu();
            menu = getValidMenu();
            AppController controller = controllers.get(menu);
            controller.handleOption(menu.getOption());
        } while (menu.isNotExit());
    }

    private void processLoginOrRegister() {
        MemberController memberController = (MemberController) controllers.get(Menu.LOGIN);

        while (true) {
            try {
                handleLoginOrRegister(memberController);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void handleLoginOrRegister(MemberController memberController) {
        int option = InputParser.parseToInt(inputView.getLoginInput());
        memberController.handleOption(option);
    }

    public Menu getValidMenu() {
        while (true) {
            try {
                return Menu.findByOption(InputParser.parseToInt(inputView.getMenuInput()));
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
