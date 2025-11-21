package global.enums;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

import java.util.Arrays;

public enum MainMenu {
    MEETING_REGISTER(1, false, "모임 등록"),
    MEETING_UPDATE(2, false, "모임 수정"),
    MEETING_DELETE(3, false, "모임 삭제"),
    MEETING_LIST(4, false, "모임 전체 조회"),
    MEMBER_LIST(5, false, "멤버 조회"),
    PARTICIPANT_REGISTER(6, false, "참여자 등록"),
    PARTICIPANT_LIST(7, false, "참여자 조회"),
    MY_MEETING_LIST(8, false, "내 모임 조회"),
    ATTENDANCE_CHECK(9, false, "출석 체크"),
    ATTENDANCE_HISTORY(10, false, "출석 기록 조회"),
    MY_NEXT_MEETING(11, false, "나의 다음 모임 조회"),
    REMIND_UPDATE(12, false, "알림 설정"),
    EXIT(13, true, "종료");


    private final int option;
    private final boolean isExit;
    private final String message;

    MainMenu(int option, boolean isExit, String message) {
        this.option = option;
        this.isExit = isExit;
        this.message = option + ". " + message;
    }

    public static MainMenu findByOption(int option) {
        return Arrays.stream(MainMenu.values())
                .filter(menu -> menu.getOption() == option)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MENU_INPUT.getMessage()));
    }

    public int getOption() {
        return option;
    }

    public String getMessage() {
        return message;
    }

    public boolean isExit() {
        return isExit;
    }
}
