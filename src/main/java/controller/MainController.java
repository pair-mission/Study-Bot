package controller;

import global.enums.Menu;
import global.utils.parser.InputParser;
import view.InputView;
import view.OutputView;

import java.util.Map;

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
        Menu menu;
        do {
            outputView.printMenu();
            menu = getValidMenu();
            AppController controller = controllers.get(menu);
            controller.controlAction(menu.getOption());
        } while (menu.isNotExit());
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
