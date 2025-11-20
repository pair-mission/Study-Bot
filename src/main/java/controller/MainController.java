package controller;

import global.config.AppConfig;
import global.enums.AuthMenu;
import global.enums.MainMenu;
import global.utils.parser.InputParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class MainController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<MainMenu, AppController> controllers;

    public MainController(InputView inputView, OutputView outputView, List<AppController> appControllers) {
        this.inputView = inputView;
        this.outputView = outputView;
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
        MemberController memberController = AppConfig.getInstance().getMemberController();
        MeetingController meetingController = AppConfig.getInstance().getMeetingController();

        while (true) {
            try {
                handleLoginOrRegister(memberController, meetingController);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void handleLoginOrRegister(MemberController memberController, MeetingController meetingController) {
        AuthMenu menu = AuthMenu.findByOption(InputParser.parseToInt(inputView.getLoginInput()));

        if (menu == AuthMenu.LOGIN) {
            memberController.login();
            meetingController.showRemindMeetings();
        }

        if (menu == AuthMenu.MEMBER_REGISTER) {
            memberController.registerMember();
            memberController.login();
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
