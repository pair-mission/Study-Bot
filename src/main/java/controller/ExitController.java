package controller;

public class ExitController implements AppController {

    @Override
    public void controlAction(int menuOption) {
        handleOption(menuOption);
    }

    @Override
    public void handleOption(int menuOption) {
        System.out.println("프로그램을 종료합니다.");
    }

}
