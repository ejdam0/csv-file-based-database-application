package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class PhoneNoErrorResponse extends GeneralDBErrorResponse {
    public PhoneNoErrorResponse(int value, String message, long currentTimeMillis) {
        super(value, message, currentTimeMillis);
    }
}
