package controller;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

import domain.member.Member;
import java.util.HashMap;
import java.util.Map;
import view.InputView;
import view.OutputView;

public abstract class AppController {
    protected final Map<Integer, Runnable> actions;
    protected final InputView inputView;
    protected final OutputView outputView;
    protected Member loginMember;

    public AppController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
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
