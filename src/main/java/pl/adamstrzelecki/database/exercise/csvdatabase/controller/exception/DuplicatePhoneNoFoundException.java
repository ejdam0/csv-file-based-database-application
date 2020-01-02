package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class DuplicatePhoneNoFoundException extends GeneralDBException {
    public DuplicatePhoneNoFoundException(String s) {
        super(s);
    }
}
