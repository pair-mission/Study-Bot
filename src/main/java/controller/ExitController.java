package controller;

import global.Session;
import view.InputView;
import view.OutputView;

public class ExitController extends AppController {

    public ExitController(InputView inputView, OutputView outputView, Session session) {
        super(inputView, outputView, session);
    }

    @Override
    protected void registerAction() {
        actions.put(11, outputView::printExit);
    }
}
