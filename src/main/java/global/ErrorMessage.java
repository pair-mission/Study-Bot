package global;

public enum ErrorMessage {

    INVALID_DATE("[ERROR] 입력 날짜는 현재날짜보다 이후여야합니다.");

    private String meesage;

    ErrorMessage(String meesage) {
        this.meesage = meesage;
    }

    public String getMeesage() {
        return this.meesage;
    }
}
