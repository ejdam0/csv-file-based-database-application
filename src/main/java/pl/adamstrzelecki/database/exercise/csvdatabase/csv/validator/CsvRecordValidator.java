package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants.FileHeaders;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants.RegularExpressions;

@Component
public class CsvRecordValidator implements Validator {

	static final Logger logger = LoggerFactory.getLogger(CsvRecordValidator.class);

	@Override
	public <T> boolean checkIfDataFulfillsConditions(T t) {

		logger.trace("=====>>CsvRecordValidator: checkIfRecordFulFillsContitions()");
		return checkIfFirstNameIsProper((CSVRecord) t) 
				&& checkIfLastNameIsProper((CSVRecord) t)
				&& checkIfBirthDateIsProper((CSVRecord) t) 
				&& checkIfPhoneNoIsProper((CSVRecord) t);
	}

	// check first name if it's null and matches regex
	private boolean checkIfFirstNameIsProper(CSVRecord record) {

		logger.trace("=====>>CsvRecordValidator: Checking whether first name is proper.");
		return record.get(FileHeaders.getUserFname()) != null
				&& record.get(FileHeaders.getUserFname()).matches(RegularExpressions.getLettersRegex());
	}

	// check last name if it's null and matches regex
	private boolean checkIfLastNameIsProper(CSVRecord record) {

		logger.trace("=====>>CsvRecordValidator: Checking whether last name is proper.");
		return record.get(FileHeaders.getUserLname()) != null
				&& record.get(FileHeaders.getUserLname()).matches(RegularExpressions.getLettersRegex());
	}

	// check birth date if it's null and matches regex
	private boolean checkIfBirthDateIsProper(CSVRecord record) {

		logger.trace("=====>>CsvRecordValidator: Checking whether birth date is proper.");
		return record.get(FileHeaders.getUserBdate()) != null
				&& record.get(FileHeaders.getUserBdate()).matches(RegularExpressions.getDateRegex());
	}

	// check phone number if it's null and matches regex
	private boolean checkIfPhoneNoIsProper(CSVRecord record) {

		logger.trace("=====>>CsvRecordValidator: Checking whether phone number is proper.");
		return record.get(FileHeaders.getUserPhoneno()) != null
				&& record.get(FileHeaders.getUserPhoneno()).matches(RegularExpressions.getNumbersRegex());
	}

}
