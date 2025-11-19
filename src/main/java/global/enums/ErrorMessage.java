package global.enums;

public enum ErrorMessage {
    // 공통
    BLANK_INPUT("[ERROR] 입력값이 비어있습니다"),
    INVALID_MENU_INPUT("[ERROR] 메뉴 입력값이 유효하지 않습니다."),
    INVALID_FILE("[ERROR] 파일이 유효하지 않습니다."),

    // 모임
    MEETING_NOT_FOUND("[ERROR] 존재하지 않는 모임입니다."),
    MEMBER_NOT_FOUND("[ERROR] 존재하지 않는 멤버입니다."),
    MEMBER_ALREADY_EXISTS("[ERROR] 해당 이름으로 가입할 수 없습니다."),
    ATTENDANCE_UNAVAILABLE("[ERROR] 모임 시작 10분 전 후만 출석할 수 있습니다."),
    SEARCH_NOT_FOUND("[ERROR] 조회 결과가 없습니다."),
    NOT_HOST("[ERROR] 모임장이 아닙니다."),
    MEMBER_NOT_PARTICIPANT("[ERROR] 참여 중인 모임이 아닙니다."),

    // 날짜
    INVALID_DATE("[ERROR] 입력 날짜는 현재날짜보다 이후여야합니다."),
    INVALID_DATE_PATTERN("[ERROR] 입력 날짜의 형식이 올바르지 않습니다."),

    // 시간
    INVALID_ENDTIME("[ERROR] 종료 시간은 시작 시작 이후여야 합니다."),
    INVALID_TIME_PATTERN("[ERROR] 입력 시간의 형식이 올바르지 않습니다."),

    // 숫자
    INVALID_NUMBER_FORMAT("[ERROR] 숫자 형식이 올바르지 않습니다."),

    // 도메인
    NULL_OR_EMPTY("[ERRROR] NULL 이거나 EMPTY 일 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
