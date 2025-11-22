package controller;

import global.Session;
import view.InputView;
import view.OutputView;

public class ControllerContext {

    private final InputView inputView;
    private final OutputView outputView;
    private final Session session;

    public ControllerContext(InputView inputView, OutputView outputView, Session session) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.session = session;
    }

    public InputView getInputView() {
        return inputView;
    }

    public OutputView getOutputView() {
        return outputView;
    }

    public Session getSession() {
        return session;
    }
}
