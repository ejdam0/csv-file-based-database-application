package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CsvRecordValidator extends Validator {

    static final Logger logger = LoggerFactory.getLogger(CsvRecordValidator.class);

    @Override
    public <T> boolean checkIfDataFulfillsConditions(T t) {
        logger.trace("=====>>UserValidator: checkIfUserFulFillsConditions()");
        return checkIfFirstNameIsProper((CSVRecord) t)
                && checkIfLastNameIsProper((CSVRecord) t)
                && checkIfBirthDateIsProper((CSVRecord) t)
                && checkIfPhoneNoIsProper((CSVRecord) t);
    }

    private boolean checkIfFirstNameIsProper(CSVRecord record) {
        logger.trace("=====>>CsvRecordValidator: Checking whether first name is proper.");
        return !GenericValidator.isBlankOrNull(record.get(FileHeaders.USER_FNAME))
                && isOnlyLetters(record.get(FileHeaders.USER_FNAME));
    }

    private boolean checkIfLastNameIsProper(CSVRecord record) {
        logger.trace("=====>>CsvRecordValidator: Checking whether last name is proper.");
        return !GenericValidator.isBlankOrNull(record.get(FileHeaders.USER_LNAME))
                && isOnlyLetters(record.get(FileHeaders.USER_LNAME));
    }

    private boolean checkIfBirthDateIsProper(CSVRecord record) {
        logger.trace("=====>>CsvRecordValidator: Checking whether birth date is proper.");
        return !GenericValidator.isBlankOrNull(record.get(FileHeaders.USER_BDATE))
                && isDateValid(record.get(FileHeaders.USER_BDATE));
    }

    private boolean checkIfPhoneNoIsProper(CSVRecord record) {
        logger.trace("=====>>CsvRecordValidator: Checking whether phone number is proper.");
        return !GenericValidator.isBlankOrNull(record.get(FileHeaders.USER_PHONENO))
                && isPhoneNumberValid(record.get(FileHeaders.USER_PHONENO));
    }
}
