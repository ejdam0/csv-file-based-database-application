package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.CsvFileReaderService;
import pl.adamstrzelecki.database.exercise.csvdatabase.dao.UserRepository;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.csv.GuiDuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.SaveToDatabaseServiceImpl;

import java.util.List;

@Service
public class SaveToDatabaseGuiServiceImpl implements SaveToDatabaseGuiService {

    static final Logger logger = LoggerFactory.getLogger(SaveToDatabaseServiceImpl.class);

    private UserRepository userRepo;
    private CsvFileReaderService csvFileReaderService;

    @Autowired
    public SaveToDatabaseGuiServiceImpl(UserRepository userRepo, CsvFileReaderService csvFileReaderService) {
        this.userRepo = userRepo;
        this.csvFileReaderService = csvFileReaderService;
    }

    @Override
    public boolean saveListToDataBase(String data) {
        logger.trace("=====>>SaveToDatabaseServiceImpl: Creating list of users via readCsvFile() from CsvFileReaderService");
        // get users from the data
        List<User> uploadedUsers = csvFileReaderService.readCsvFile(data);
        if (uploadedUsers.isEmpty()) {
            logger.trace("=====>>SaveToDatabaseServiceImpl: Uploaded users list is empty!");
            return false;
        }
        logger.trace("=====>>SaveToDatabaseServiceImpl: Getting users from the database");
        // get users from the database
        List<User> usersFromDatabase = getUsersFromDatabase();
        logger.trace(
                "=====>>SaveToDatabaseServiceImpl: Looking for duplicate phone numbers via searchForDuplicatePhoneNumbers() from DuplicatePhoneNoFinder");
        // look for duplicates
        if (GuiDuplicatePhoneNoFinder.searchForDuplicatePhoneNumbers(uploadedUsers, usersFromDatabase)) {
            // if there are duplicates -> return false
            return false;
        }
        logger.trace("=====>>SaveToDatabaseServiceImpl: Saving all users to the database");
        // add users to the database
        userRepo.saveAll(uploadedUsers);
        return true;
    }

    // helper method to check for duplicates
    private List<User> getUsersFromDatabase() {
        return userRepo.findAll();
    }
}
