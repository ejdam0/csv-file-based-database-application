package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception.DuplicatePhoneNoFoundException;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

@Component
public class DuplicatePhoneNoFinder {

	static final Logger logger = LoggerFactory.getLogger(DuplicatePhoneNoFinder.class);

	public static void searchForDuplicatePhoneNumbers(List<User> uploadedUsers, List<User> databaseUsers) {

		// list containing phone numbers of the uploaded users
		List<String> uploadedUserslistOfPhoneNo = new ArrayList<String>();
		// list containing phone numbers of the database users
		List<String> databaseUserslistOfPhoneNo = new ArrayList<String>();

		// fill the lists
		logger.trace("=====>>DuplicatePhoneNoFinder: Filling list with uploaded phone numbers");
		for (User u : uploadedUsers) {
			uploadedUserslistOfPhoneNo.add(u.getPhoneNo());
		}

		logger.trace("=====>>DuplicatePhoneNoFinder: Calling hasDuplicate() method on the uploaded data");
		hasDuplicate(uploadedUserslistOfPhoneNo);

		logger.trace("=====>>DuplicatePhoneNoFinder: Filling list with database phone numbers");
		for (User u : databaseUsers) {
			databaseUserslistOfPhoneNo.add(u.getPhoneNo());
		}

		logger.trace(
				"=====>>DuplicatePhoneNoFinder: Checking if there are any duplicates of phone numbers in database and uploaded data");

		// list containing duplicate phone numbers
		List<String> possibleDuplicates = new ArrayList<String>(databaseUserslistOfPhoneNo);
		possibleDuplicates.retainAll(uploadedUserslistOfPhoneNo);

		if (possibleDuplicates.isEmpty()) {
			logger.trace("=====>>DuplicatePhoneNoFinder: No duplicates found");
			logger.trace("=====>>DuplicatePhoneNoFinder: Returning to SaveToDatabaseServiceImpl");
		} else {
			logger.warn("=====>>DuplicatePhoneNoFinder: There are duplicate phone numbers in the data and database!");
			throw new DuplicatePhoneNoFoundException("There are duplicate numbers in the uploaded data and database!");
		}

	}

	private static boolean hasDuplicate(List<String> uploadedUserslistOfPhoneNo) {

		logger.trace("=====>>DuplicatePhoneNoFinder: hasDuplicate(): Looking for duplicates in the uploaded data");

		// set containing phone numbers of users (no duplicate values)
		logger.trace("=====>>DuplicatePhoneNoFinder: Creating a set out of the phone numbers list");
		Set<String> setOfPhoneNumbers = new HashSet<String>(uploadedUserslistOfPhoneNo);

		// check if there are duplicates (set size will be smaller)
		logger.trace("=====>>DuplicatePhoneNoFinder: Checking if there are any duplicates of phone numbers");

		if (setOfPhoneNumbers.size() < uploadedUserslistOfPhoneNo.size()) {

			logger.warn("=====>>DuplicatePhoneNoFinder: There are duplicate phone numbers in the data!");
			throw new DuplicatePhoneNoFoundException("There are duplicate numbers in the uploaded data!");
		}

		logger.trace("=====>>DuplicatePhoneNoFinder: No duplicates found");
		return false;
	}

}
