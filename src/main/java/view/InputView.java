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
        System.out.println("\n등록할 멤버의 닉네임을 입력하세요.");
        return Console.readLine();
    }

    public String getMeetingUpdateInput() {
        System.out.println("\n수정할 모임의 ID와 새로운 정보를 순서대로 입력해주세요. (예: 2, 코테 1회차, 스터디룸A)\n");
        return Console.readLine();
    }

    public String getMeetingDeleteInput() {
        System.out.println("\n삭제할 모임의 ID 를 입력해주세요.");
        return Console.readLine();
    }

    public String getParticipantRegisterInput() {
        System.out.println("\n참여할 모임의 ID를 입력하세요.");
        return Console.readLine();
    }

    public String getParticipantMemberInput() {
        System.out.println("\n참여자를 조회할 모임의 ID를 입력하세요.");
        return Console.readLine();
    }

}
