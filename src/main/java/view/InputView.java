package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public String getUserNickname() {
        System.out.print("\n닉네임을 입력하세요: ");
        return Console.readLine();
    }

    public String getMenuInput() {
        System.out.print("\n수행할 기능의 번호를 입력하세요: ");
        return Console.readLine();
    }

    public String getMeetingCreationInput() {
        System.out.println("\n모임 정보를 입력하세요 (예: 2025-11-11, 19:00, 21:00, 자바스터디, 강남역)");
        return Console.readLine();
    }

    public String getMemberNickname() {
        System.out.println("\n 등록할 멤버의 닉네임을 입력하세요.");
        return Console.readLine();
    }
}
