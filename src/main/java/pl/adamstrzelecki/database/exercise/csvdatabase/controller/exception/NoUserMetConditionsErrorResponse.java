package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class NoUserMetConditionsErrorResponse extends GeneralDBErrorResponse {
    public NoUserMetConditionsErrorResponse(int value, String message, long currentTimeMillis) {
        super(value, message, currentTimeMillis);
    }
}
