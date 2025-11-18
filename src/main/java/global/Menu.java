package global;

import java.util.Arrays;

import static global.ErrorMessage.INVALID_MENU_INPUT;

public enum Menu {
    MEETING_REGISTER(1, false),
    MEETING_UPDATE(2, false),
    MEETING_DELETE(3, false),
    MEETING_LIST(4, false),
    MEMBER_LIST(5, false),
    MEMBER_REGISTER(6, false),
    PARTICIPANT_REGISTER(7, false),
    PARTICIPANT_LIST(8, false),
    MY_MEETING_LIST(10, false),
    EXIT(11, true);

    private final int option;
    private final boolean isExit;

    Menu(int option, boolean isExit) {
        this.option = option;
        this.isExit = isExit;
    }

    public int getOption() {
        return option;
    }

    public boolean isNotExit() {
        return !isExit;
    }

    public static Menu findByOption(int option) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getOption() == option)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MENU_INPUT.getMessage()));
    }
}
