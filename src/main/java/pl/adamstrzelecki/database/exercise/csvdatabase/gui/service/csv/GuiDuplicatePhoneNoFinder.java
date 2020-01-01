package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.DuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GuiDuplicatePhoneNoFinder {

    static final Logger logger = LoggerFactory.getLogger(DuplicatePhoneNoFinder.class);

    public static boolean searchForDuplicatePhoneNumbers(List<User> uploadedUsers, List<User> databaseUsers) {
        // list containing phone numbers of the uploaded users
        List<String> uploadedUsersListOfPhoneNo = new ArrayList<>();
        // list containing phone numbers of the database users
        List<String> databaseUsersListOfPhoneNo = new ArrayList<>();

        // fill the lists
        logger.trace("=====>>DuplicatePhoneNoFinder: Filling list with uploaded phone numbers");
        for (User u : uploadedUsers) {
            uploadedUsersListOfPhoneNo.add(u.getPhoneNo());
        }

        logger.trace("=====>>DuplicatePhoneNoFinder: Calling hasDuplicate() method on the uploaded data");
        if (hasDuplicate(uploadedUsersListOfPhoneNo)) {
            // if there are duplicates in uploaded data -> return true
            return true;
        }

        logger.trace("=====>>DuplicatePhoneNoFinder: Filling list with database phone numbers");
        for (User u : databaseUsers) {
            databaseUsersListOfPhoneNo.add(u.getPhoneNo());
        }

        logger.trace("=====>>DuplicatePhoneNoFinder: Checking if there are any duplicates of phone numbers in database and uploaded data");

        // list containing duplicate phone numbers
        List<String> possibleDuplicates = new ArrayList<>(databaseUsersListOfPhoneNo);
        possibleDuplicates.retainAll(uploadedUsersListOfPhoneNo);

        if (possibleDuplicates.isEmpty()) {
            logger.trace("=====>>DuplicatePhoneNoFinder: No duplicates found");
            logger.trace("=====>>DuplicatePhoneNoFinder: Returning to SaveToDatabaseServiceImpl");
            return false;
        } else {
            logger.warn("=====>>DuplicatePhoneNoFinder: There are duplicate phone numbers in the data and database!");
            return true;
        }
    }

    private static boolean hasDuplicate(List<String> uploadedUsersListOfPhoneNo) {

        logger.trace("=====>>DuplicatePhoneNoFinder: hasDuplicate(): Looking for duplicates in the uploaded data");

        // set containing phone numbers of users (no duplicate values)
        logger.trace("=====>>DuplicatePhoneNoFinder: Creating a set out of the phone numbers list");
        Set<String> setOfPhoneNumbers = new HashSet<>(uploadedUsersListOfPhoneNo);

        // check if there are duplicates (set size will be smaller)
        logger.trace("=====>>DuplicatePhoneNoFinder: Checking if there are any duplicates of phone numbers");

        if (setOfPhoneNumbers.size() < uploadedUsersListOfPhoneNo.size()) {
            logger.warn("=====>>DuplicatePhoneNoFinder: There are duplicate phone numbers in the data!");
            return true;
        }

        logger.trace("=====>>DuplicatePhoneNoFinder: No duplicates found");
        return false;
    }


}
