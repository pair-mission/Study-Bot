package global.enums;

public enum Pattern {
    DATE_PATTERN("^\\d{4}-\\d{2}-\\d{2}$"),
    TIME_PATTERN("^([01]\\d|2[0-3]):(00|30)$");

    private final String value;

    Pattern(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
