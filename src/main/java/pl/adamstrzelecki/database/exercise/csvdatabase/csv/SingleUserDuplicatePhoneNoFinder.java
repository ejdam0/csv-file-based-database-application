package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

@Component
public class SingleUserDuplicatePhoneNoFinder {

	static final Logger logger = LoggerFactory.getLogger(SingleUserDuplicatePhoneNoFinder.class);

	public static boolean searchForDuplicates(List<User> databaseUsers, User user) {

		// list containing phone numbers of the database users
		List<String> databaseUserslistOfPhoneNo = new ArrayList<>();

		logger.trace("=====>>SingleUserDuplicatePhoneNoFinder: Filling list with database phone numbers");
		for (User u : databaseUsers) {
			databaseUserslistOfPhoneNo.add(u.getPhoneNo());
		}

		logger.trace("=====>>SingleUserDuplicatePhoneNoFinder: Checking if the number exists in the list");
		return databaseUserslistOfPhoneNo.contains(user.getPhoneNo());

	}

}
