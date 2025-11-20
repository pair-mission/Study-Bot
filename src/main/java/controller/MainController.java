package controller;

import global.enums.AuthMenu;
import global.enums.MainMenu;
import global.utils.parser.InputParser;
import view.InputView;
import view.OutputView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AuthHandler authHandler;
    private final RemindHandler remindHandler;
    private final Map<MainMenu, AppController> controllers;

    public MainController(InputView inputView, OutputView outputView,
                          List<AppController> appControllers,
                          AuthHandler authHandler, RemindHandler remindHandler) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.authHandler = authHandler;
        this.remindHandler = remindHandler;
        this.controllers = new HashMap<>();
        initializeControllers(appControllers);
    }

    private void initializeControllers(List<AppController> appControllers) {
        for (AppController appController : appControllers) {
            for (MainMenu menu : appController.getSupportMenus()) {
                controllers.put(menu, appController);
            }
        }
    }

    public void run() {
        processLoginOrRegister();

        MainMenu menu;
        do {
            outputView.printMenu();
            menu = getValidMenu();
            AppController controller = controllers.get(menu);
            controller.handleOption(menu);
        } while (menu.isNotExit());
    }

    private void processLoginOrRegister() {
        while (true) {
            try {
                handleLoginOrRegister(authHandler, remindHandler);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void handleLoginOrRegister(AuthHandler authHandler, RemindHandler remindHandler) {
        AuthMenu menu = AuthMenu.findByOption(InputParser.parseToInt(inputView.getLoginInput()));

        if (menu == AuthMenu.LOGIN) {
            authHandler.login();
            remindHandler.showRemindMeetings();
        }

        if (menu == AuthMenu.MEMBER_REGISTER) {
            authHandler.registerMember();
            authHandler.login();
        }
    }

    public MainMenu getValidMenu() {
        while (true) {
            try {
                return MainMenu.findByOption(InputParser.parseToInt(inputView.getMenuInput()));
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
