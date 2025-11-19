package controller;

import global.Session;
import view.InputView;
import view.OutputView;

import static global.enums.Menu.EXIT;

public class ExitController extends AppController {

    public ExitController(InputView inputView, OutputView outputView, Session session) {
        super(inputView, outputView, session);
    }

    @Override
    protected void registerAction() {
        actions.put(EXIT.getOption(), outputView::printExit);
    }
}
