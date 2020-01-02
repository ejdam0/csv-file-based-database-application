package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class UserErrorResponse extends GeneralDBErrorResponse {
    public UserErrorResponse(int value, String message, long currentTimeMillis) {
        super(value, message, currentTimeMillis);
    }
}
