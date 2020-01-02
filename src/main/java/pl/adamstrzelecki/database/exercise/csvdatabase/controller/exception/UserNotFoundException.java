package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class UserNotFoundException extends GeneralDBException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
