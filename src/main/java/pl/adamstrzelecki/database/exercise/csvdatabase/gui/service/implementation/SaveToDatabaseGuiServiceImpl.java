package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.CsvFileReaderService;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.DuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.dao.UserRepository;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.SaveToDatabaseGuiService;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.implementation.SaveToDatabaseServiceImpl;

import java.util.List;

@Service
public class SaveToDatabaseGuiServiceImpl implements SaveToDatabaseGuiService {

    static final Logger logger = LoggerFactory.getLogger(SaveToDatabaseServiceImpl.class);

    private UserRepository userRepo;
    private CsvFileReaderService csvFileReaderService;
    private DuplicatePhoneNoFinder duplicatePhoneNoFinder;

    @Autowired
    public SaveToDatabaseGuiServiceImpl(UserRepository userRepo, CsvFileReaderService csvFileReaderService, DuplicatePhoneNoFinder duplicatePhoneNoFinder) {
        this.userRepo = userRepo;
        this.csvFileReaderService = csvFileReaderService;
        this.duplicatePhoneNoFinder = duplicatePhoneNoFinder;
    }

    @Override
    public boolean saveListToDatabase(String data) {
        logger.trace("=====>>SaveToDatabaseGuiServiceImpl: Creating list of users via readCsvFile() from CsvFileReaderService");
        // get users from the data
        List<User> uploadedUsers = csvFileReaderService.readCsvFile(data);
        if (uploadedUsers.isEmpty()) {
            logger.trace("=====>>SaveToDatabaseGuiServiceImpl: Uploaded users list is empty!");
            return false;
        }
        logger.trace("=====>>SaveToDatabaseGuiServiceImpl: Getting users from the database");
        // get users from the database
        List<User> usersFromDatabase = getUsersFromDatabase();
        logger.trace("=====>>SaveToDatabaseGuiServiceImpl: Looking for duplicate phone numbers via searchForDuplicatePhoneNumbers() from DuplicatePhoneNoFinder");
        // look for duplicates
        if (duplicatePhoneNoFinder.searchForDuplicatePhoneNumbers(uploadedUsers, usersFromDatabase)) {
            // if there are duplicates -> return false
            return false;
        }
        logger.trace("=====>>SaveToDatabaseGuiServiceImpl: Saving all users to the database");
        // add users to the database
        userRepo.saveAll(uploadedUsers);
        return true;
    }

    // helper method to check for duplicates
    private List<User> getUsersFromDatabase() {
        return userRepo.findAll();
    }
}
