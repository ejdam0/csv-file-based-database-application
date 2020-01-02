package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

@Component
public class UserValidator extends Validator {

    static final Logger logger = LoggerFactory.getLogger(UserValidator.class);

    @Override
    public <T> boolean checkIfDataFulfillsConditions(T t) {
        logger.trace("=====>>UserValidator: checkIfUserFulFillsConditions()");
        return checkIfFirstNameIsProper((User) t)
                && checkIfLastNameIsProper((User) t)
                && checkIfBirthDateIsProper((User) t)
                && checkIfPhoneNoIsProper((User) t);
    }

    private boolean checkIfFirstNameIsProper(User user) {
        logger.trace("=====>>UserValidator: Checking whether first name is proper.");
        return !GenericValidator.isBlankOrNull(user.getFirstName())
                && isOnlyLetters(user.getFirstName());
    }

    private boolean checkIfLastNameIsProper(User user) {
        logger.trace("=====>>UserValidator: Checking whether last name is proper.");
        return !GenericValidator.isBlankOrNull(user.getLastName())
                && isOnlyLetters(user.getLastName());
    }

    private boolean checkIfBirthDateIsProper(User user) {
        logger.trace("=====>>UserValidator: Checking whether birth date is proper.");
        return !GenericValidator.isBlankOrNull(user.getBirthDate())
                && isDateValid(user.getBirthDate());
    }

    private boolean checkIfPhoneNoIsProper(User user) {
        logger.trace("=====>>UserValidator: Checking whether phone number is proper.");
        return !GenericValidator.isBlankOrNull(user.getPhoneNo())
                && isPhoneNumberValid(user.getPhoneNo());
    }
}
