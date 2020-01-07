package pl.adamstrzelecki.database.exercise.csvdatabase.csv.implementation;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.DataToTheListAdder;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.FileHeaders;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.Validator;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

@Service
public class DataToTheListAdderImpl implements DataToTheListAdder {

    static final Logger logger = LoggerFactory.getLogger(DataToTheListAdderImpl.class);

    private Validator csvRecordValidator;

    @Autowired
    public DataToTheListAdderImpl(Validator csvRecordValidator) {
        this.csvRecordValidator = csvRecordValidator;
    }

    @Override
    public void addUserToTheList(List<User> users, List<CSVRecord> csvRecords) {

        // read the CSV file records starting from the first row
        logger.trace("=====>>DataToTheListAdder: Starting loop and adding users to the list");
        csvRecords.stream()
                .filter(r -> csvRecordValidator.checkIfDataFulfillsConditions(r))
                .forEach(r -> {
                    User u = new User(StringUtils.capitalize(r.get(FileHeaders.USER_FNAME)),
                            StringUtils.capitalize(r.get(FileHeaders.USER_LNAME)),
                            r.get(FileHeaders.USER_BDATE),
                            r.get(FileHeaders.USER_PHONENO));
                    users.add(u);
                });
    }
}
