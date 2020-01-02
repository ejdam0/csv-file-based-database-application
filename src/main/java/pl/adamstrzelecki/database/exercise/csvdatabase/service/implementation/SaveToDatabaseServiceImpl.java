package pl.adamstrzelecki.database.exercise.csvdatabase.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception.DuplicatePhoneNoFoundException;
import pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception.NoUserMetConditionsException;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.CsvFileReaderService;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.DuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.dao.UserRepository;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.SaveToDatabaseService;

import java.util.List;

@Service
public class SaveToDatabaseServiceImpl implements SaveToDatabaseService {

    static final Logger logger = LoggerFactory.getLogger(SaveToDatabaseServiceImpl.class);

    private UserRepository userRepo;
    private CsvFileReaderService csvFileReaderService;
    private DuplicatePhoneNoFinder duplicatePhoneNoFinder;

    @Autowired
    public SaveToDatabaseServiceImpl(UserRepository userRepo, CsvFileReaderService csvFileReaderService, DuplicatePhoneNoFinder duplicatePhoneNoFinder) {
        this.userRepo = userRepo;
        this.csvFileReaderService = csvFileReaderService;
        this.duplicatePhoneNoFinder = duplicatePhoneNoFinder;
    }

    @Override
    public long saveListToDataBase(String data) {
        logger.trace("=====>>SaveToDatabaseServiceImpl: Creating list of users via readCsvFile() from CsvFileReaderService");
        // get users from the data
        List<User> uploadedUsers = csvFileReaderService.readCsvFile(data);
        if (uploadedUsers.isEmpty()) {
            throw new NoUserMetConditionsException("The data contains invalid records!");
        }
        logger.trace("=====>>SaveToDatabaseServiceImpl: Getting users from the database");
        // get users from the database
        List<User> usersFromDatabase = getUsersFromDatabase();
        logger.trace("=====>>SaveToDatabaseServiceImpl: Looking for duplicate phone numbers via searchForDuplicatePhoneNumbers() from DuplicatePhoneNoFinder");
        // look for duplicates
        if (duplicatePhoneNoFinder.searchForDuplicatePhoneNumbers(uploadedUsers, usersFromDatabase)) {
            throw new DuplicatePhoneNoFoundException("There are duplicate phone numbers in uploaded data and database!");
        }
        logger.trace("=====>>SaveToDatabaseServiceImpl: Saving all users to the database");
        // add users to the database
        userRepo.saveAll(uploadedUsers);
        return uploadedUsers.size();
    }

    // helper method to check for duplicates
    private List<User> getUsersFromDatabase() {
        return userRepo.findAll();
    }
}
