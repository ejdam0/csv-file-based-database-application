package pl.adamstrzelecki.database.exercise.csvdatabase.csv.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.SingleUserDuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class SingleUserDuplicatePhoneNoFinderImpl implements SingleUserDuplicatePhoneNoFinder {
    static final Logger logger = LoggerFactory.getLogger(SingleUserDuplicatePhoneNoFinderImpl.class);

    public boolean searchForDuplicates(List<User> databaseUsers, User user) {
        if (user.getId() != 0) {
            return false;
        }

        List<String> databaseUserslistOfPhoneNo = new ArrayList<>();

        logger.trace("=====>>SingleUserDuplicatePhoneNoFinder: Filling list with database phone numbers");
        for (User u : databaseUsers) {
            databaseUserslistOfPhoneNo.add(u.getPhoneNo());
        }

        logger.trace("=====>>SingleUserDuplicatePhoneNoFinder: Checking if the number exists in the list");
        return databaseUserslistOfPhoneNo.contains(user.getPhoneNo());
    }
}
