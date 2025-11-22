package controller;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

import global.Session;
import global.enums.MainMenu;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import view.InputView;
import view.OutputView;

public abstract class AppController {

    protected final Map<MainMenu, Runnable> actions;
    protected final InputView inputView;
    protected final OutputView outputView;
    protected final Session session;

    public AppController(ControllerContext context) {
        this.inputView = context.getInputView();
        this.outputView = context.getOutputView();
        this.session = context.getSession();
        this.actions = new HashMap<>();
        registerAction();
    }

    protected abstract void registerAction();

    void handleOption(MainMenu menu) {
        Runnable action = actions.get(menu);
        if (action == null) {
            outputView.printErrorMessage(INVALID_MENU_INPUT.getMessage());
            return;
        }
        action.run();
    }

    Set<MainMenu> getSupportMenus() {
        return actions.keySet();
    }

}
