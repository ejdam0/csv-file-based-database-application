package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants.RegularExpressions;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

@Component
public class UserValidator implements Validator {

	static final Logger logger = LoggerFactory.getLogger(CsvRecordValidator.class);

	@Override
	public <T> boolean checkIfDataFulfillsConditions(T t) {

		logger.trace("=====>>UserValidator: checkIfUserFulFillsConditions()");
		return checkIfFirstNameIsProper((User) t) 
				&& checkIfLastNameIsProper((User) t)
				&& checkIfBirthDateIsProper((User) t) 
				&& checkIfPhoneNoIsProper((User) t);
	}

	// check first name if it's null and matches regex
	private boolean checkIfFirstNameIsProper(User user) {

		logger.trace("=====>>UserValidator: Checking whether first name is proper.");
		return user.getFirstName() != null && user.getFirstName().matches(RegularExpressions.getLettersRegex());
	}

	// check last name if it's null and matches regex
	private boolean checkIfLastNameIsProper(User user) {

		logger.trace("=====>>UserValidator: Checking whether last name is proper.");
		return user.getLastName() != null && user.getLastName().matches(RegularExpressions.getLettersRegex());
	}

	// check birth date if it's null and matches regex
	private boolean checkIfBirthDateIsProper(User user) {

		logger.trace("=====>>UserValidator: Checking whether birth date is proper.");
		return user.getBirthDate() != null && user.getBirthDate().matches(RegularExpressions.getDateRegex());
	}

	// check phone number if it's null and matches regex
	private boolean checkIfPhoneNoIsProper(User user) {

		logger.trace("=====>>UserValidator: Checking whether phone number is proper.");
		return user.getPhoneNo() != null && user.getPhoneNo().matches(RegularExpressions.getNumbersRegex());
	}

}
