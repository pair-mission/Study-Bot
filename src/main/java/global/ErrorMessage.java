package global;

public enum ErrorMessage {

    // 날짜
    INVALID_DATE("[ERROR] 입력 날짜는 현재날짜보다 이후여야합니다."),
    INVALID_DATE_PATTERN("[ERROR] 입력 날짜의 형식이 올바르지 않습니다."),
    BLANK_INPUT("[ERROR] 입력값이 비어있습니다"),

    // 시간
    INVALID_ENDTIME("[ERROR] 종료 시간은 시작 시작 이후여야 합니다.");
    
    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
