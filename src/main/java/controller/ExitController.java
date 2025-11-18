package controller;

import view.InputView;
import view.OutputView;

public class ExitController extends AppController {

    public ExitController(InputView inputView, OutputView outputView) {
        super(inputView, outputView);
    }

    @Override
    protected void registerAction() {
        actions.put(11, outputView::printExit);
    }
}
