package global.enums;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

import java.util.Arrays;

public enum AuthMenu {
    LOGIN(1),
    MEMBER_REGISTER(2);

    private final int option;

    AuthMenu(int option) {
        this.option = option;
    }

    public static AuthMenu findByOption(int option) {
        return Arrays.stream(AuthMenu.values())
                .filter(menu -> menu.getOption() == option)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MENU_INPUT.getMessage()));
    }

    public int getOption() {
        return option;
    }
}
