package view;

import camp.nextstep.edu.missionutils.Console;
import global.utils.InputValidator;

public class InputView {

    public String getLoginInput() {
        System.out.println("""
                                  ▗▖     ▗▖
                      ▐▌          ▐▌     ▐▌         ▐▌
                ▗▟██▖▐███ ▐▌ ▐▌ ▟█▟▌▝█ █▌▐▙█▙  ▟█▙ ▐███
                ▐▙▄▖▘ ▐▌  ▐▌ ▐▌▐▛ ▜▌ █▖█ ▐▛ ▜▌▐▛ ▜▌ ▐▌
                 ▀▀█▖ ▐▌  ▐▌ ▐▌▐▌ ▐▌ ▐█▛ ▐▌ ▐▌▐▌ ▐▌ ▐▌
                ▐▄▄▟▌ ▐▙▄ ▐▙▄█▌▝█▄█▌  █▌ ▐█▄█▘▝█▄█▘ ▐▙▄
                 ▀▀▀   ▀▀  ▀▀▝▘ ▝▀▝▘  █  ▝▘▀▘  ▝▀▘   ▀▀
                                     █▌
                """);
        System.out.println("안녕하세요. 스터디봇입니다.\n1. 로그인\n2. 멤버 등록");
        return readNonBlankLine();
    }

    public String getUserNickname() {
        System.out.print("\n닉네임을 입력하세요: ");
        return readNonBlankLine();
    }

    public String getMenuInput() {
        System.out.print("\n수행할 기능의 번호를 입력하세요: ");
        return readNonBlankLine();
    }

    public String getMeetingCreationInput() {
        System.out.println("\n모임 정보를 입력하세요 (예: 2025-11-11, 19:00, 21:00, 자바스터디, 강남역)");
        return readNonBlankLine();
    }

    public String getMemberNickname() {
        System.out.println("\n등록할 멤버의 닉네임을 입력하세요.");
        return readNonBlankLine();
    }

    public String getMeetingUpdateInput() {
        System.out.println("\n수정할 모임의 ID와 새로운 정보를 순서대로 입력해주세요. (예: 2, 코테 1회차, 스터디룸A)\n");
        return readNonBlankLine();
    }

    public String getMeetingDeleteInput() {
        System.out.println("\n삭제할 모임의 ID 를 입력해주세요.");
        return readNonBlankLine();
    }

    public String getParticipantRegisterInput() {
        System.out.println("\n참여할 모임의 ID를 입력하세요.");
        return readNonBlankLine();
    }

    public String getParticipantMemberInput() {
        System.out.println("\n참여자를 조회할 모임의 ID를 입력하세요.");
        return readNonBlankLine();
    }

    public String getAttendanceInput() {
        System.out.println("\n 출석할 모임의 ID를 입력하세요.");
        return readNonBlankLine();
    }

    public String getAttendanceHistoryInput() {
        System.out.println("\n 출석 기록을 조회할 모임의 ID를 입력하세요.");
        return readNonBlankLine();
    }

    public String getRemindDay() {
        System.out.println("\n[일정 리마인드 설정]\n모임 시작하기 며칠 전에 알려드릴까요?");
        return readNonBlankLine();
    }

    private String readNonBlankLine() {
        String input = Console.readLine();
        InputValidator.validateBlankInput(input);
        return input;
    }
}
