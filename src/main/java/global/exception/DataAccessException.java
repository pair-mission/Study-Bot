package global.exception;

import global.ErrorMessage;

public class DataAccessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public DataAccessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage.getMessage();
    }

}
