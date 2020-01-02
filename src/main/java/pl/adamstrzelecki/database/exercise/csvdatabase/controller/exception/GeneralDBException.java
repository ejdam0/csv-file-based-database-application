package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class GeneralDBException extends RuntimeException {

    public GeneralDBException() {

    }

    public GeneralDBException(String message) {
        super(message);

    }

    public GeneralDBException(Throwable cause) {
        super(cause);

    }

    public GeneralDBException(String message, Throwable cause) {
        super(message, cause);

    }

    public GeneralDBException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }


}
