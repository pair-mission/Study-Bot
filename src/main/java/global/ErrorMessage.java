package global;

public enum ErrorMessage {
    // 공통
    BLANK_INPUT("[ERROR] 입력값이 비어있습니다"),

    // 모임
    MEETING_NOT_FOUND("존재하지 않는 모임입니다."),
    MEMBER_NOT_FOUND("존재하지 않는 멤버입니다."),

    // 날짜
    INVALID_DATE("[ERROR] 입력 날짜는 현재날짜보다 이후여야합니다."),
    INVALID_DATE_PATTERN("[ERROR] 입력 날짜의 형식이 올바르지 않습니다."),

    // 시간
    INVALID_ENDTIME("[ERROR] 종료 시간은 시작 시작 이후여야 합니다."),
    INVALID_TIME_PATTERN("[ERROR] 입력 시간의 형식이 올바르지 않습니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
