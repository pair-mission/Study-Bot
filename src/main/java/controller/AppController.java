package controller;

import global.Session;
import view.InputView;
import view.OutputView;

import java.util.HashMap;
import java.util.Map;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

public abstract class AppController {
    protected final Map<Integer, Runnable> actions;
    protected final InputView inputView;
    protected final OutputView outputView;
    protected final Session session;

    public AppController(InputView inputView, OutputView outputView, Session session) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.session = session;
        this.actions = new HashMap<>();
        registerAction();
    }

    protected abstract void registerAction();

    void handleOption(int menuOption) {
        Runnable action = actions.get(menuOption);
        if (action == null) {
            outputView.printErrorMessage(INVALID_MENU_INPUT.getMessage());
            return;
        }
        action.run();
    }

}
