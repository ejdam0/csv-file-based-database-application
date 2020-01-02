package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

import org.apache.commons.validator.GenericValidator;

public abstract class Validator {

    public abstract <T> boolean checkIfDataFulfillsConditions(T t);

    boolean isOnlyLetters(String name) {
        return GenericValidator.matchRegexp(name, "\\p{L}+");
    }

    boolean isDateValid(String date) {
        return GenericValidator.isDate(date, "yyyy.MM.dd", true);
    }

    boolean isPhoneNumberValid(String phoneNumber) {
        return GenericValidator.isInt(phoneNumber)
                && GenericValidator.maxLength(phoneNumber, 9)
                && GenericValidator.minLength(phoneNumber, 9);
    }
}
