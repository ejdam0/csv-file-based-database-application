package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.csv;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.DataToTheListAdderImpl;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.Validator;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants.FileHeaders;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

@Service
public class GuiDataToTheListAdderImpl implements GuiDataToTheListAdder {

    static final Logger logger = LoggerFactory.getLogger(DataToTheListAdderImpl.class);

    private Validator csvRecordValidator;

    @Autowired
    public GuiDataToTheListAdderImpl(Validator csvRecordValidator) {
        this.csvRecordValidator = csvRecordValidator;
    }

    @Override
    public void addUserToTheList(List<User> users, List<CSVRecord> csvRecords) {

        // read the CSV file records starting from the first row
        logger.trace("=====>>DataToTheListAdder: Starting loop and adding users to the list");
        for (int i = 0; i < csvRecords.size(); i++) {

            logger.trace("=====>>DataToTheListAdder: Getting " + i + " record from the csvRecord list");
            CSVRecord record = csvRecords.get(i);

            logger.trace("=====>>DataToTheListAdder: Checking whether the record meets the conditions");
            if (csvRecordValidator.checkIfDataFulfillsConditions(record)) {

                // create a new user object and fill his data
                // capitalize first letter of the first name and last name
                logger.trace(
                        "=====>>DataToTheListAdder: Record fulfills conditions. Creating user object and filling its data");
                User user = new User(
                        StringUtils.capitalize(record.get(FileHeaders.getUserFname())),
                        StringUtils.capitalize(record.get(FileHeaders.getUserLname())),
                        record.get(FileHeaders.getUserBdate()),
                        record.get(FileHeaders.getUserPhoneno())
                );

                logger.trace("=====>>DataToTheListAdder: Adding created user to the list");
                users.add(user);
            }
        }
    }

}
