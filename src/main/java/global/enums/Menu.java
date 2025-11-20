package global.enums;

import java.util.Arrays;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

public enum Menu {
    MEETING_REGISTER(1, false),
    MEETING_UPDATE(2, false),
    MEETING_DELETE(3, false),
    MEETING_LIST(4, false),
    MEMBER_LIST(5, false),
    MEMBER_REGISTER(6, false),
    PARTICIPANT_REGISTER(7, false),
    PARTICIPANT_LIST(8, false),
    MY_MEETING_LIST(9, false),
    ATTENDANCE_CHECK(10, false),
    ATTENDANCE_HISTORY(11, false),
    MY_NEXT_MEETING(12, false),
    REMIND_UPDATE(13, false),
    EXIT(14, true),
    LOGIN(0, false);

    private final int option;
    private final boolean isExit;

    Menu(int option, boolean isExit) {
        this.option = option;
        this.isExit = isExit;
    }

    public static Menu findByOption(int option) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getOption() == option)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MENU_INPUT.getMessage()));
    }

    public int getOption() {
        return option;
    }

    public boolean isNotExit() {
        return !isExit;
    }
}
