package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.implementation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.SingleUserDuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.Validator;
import pl.adamstrzelecki.database.exercise.csvdatabase.dao.UserRepository;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.UserGuiService;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.implementation.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class UserGuiServiceImpl implements UserGuiService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private Validator userValidator;
    private SingleUserDuplicatePhoneNoFinder singleUserDuplicatePhoneNoFinder;

    @Autowired
    public UserGuiServiceImpl(UserRepository userRepository, Validator userValidator, SingleUserDuplicatePhoneNoFinder singleUserDuplicatePhoneNoFinder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.singleUserDuplicatePhoneNoFinder = singleUserDuplicatePhoneNoFinder;
    }

    @Override
    public List<User> findAll() {
        logger.trace("=====>>UserGuiServiceImpl: findAll()");
        return userRepository.findAll();
    }

    @Override
    public User findById(int theId) {
        logger.trace("=====>>UserGuiServiceImpl: Searching for user using id");
        Optional<User> result = userRepository.findById(theId);
        return result.get();
    }

    @Override
    public boolean save(User user) {
        logger.trace("=====>>UserGuiServiceImpl: Verifying the provided User data");
        if (!userValidator.checkIfDataFulfillsConditions(user)) {
            logger.trace("=====>>UserGuiServiceImpl: Provided data is incorrect");
            return false;
        }

        logger.trace("=====>>UserGuiServiceImpl: Data is correct");
        logger.trace("=====>>UserGuiServiceImpl: Looking for duplicate phone numbers in the database");
        List<User> users = userRepository.findAll();
        if (singleUserDuplicatePhoneNoFinder.searchForDuplicates(users, user)) {
            logger.trace("=====>>UserGuiServiceImpl: Number already exists in the database");
            return false;
        }

        logger.trace("=====>>UserGuiServiceImpl: User data correct. Saving new user");
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteById(int theId) {
        logger.trace("=====>>UserGuiServiceImpl: Searching for user using id");
        Optional<User> result = userRepository.findById(theId);
        if (result.isEmpty()) {
            logger.error("=====>>UserGuiServiceImpl: User not found");
            return false;
        } else {
            logger.trace("=====>>UserGuiServiceImpl: Deleting user using id");
            userRepository.deleteById(theId);
        }
        return true;
    }

    @Override
    public void deleteAll() {
        logger.trace("=====>>UserGuiServiceImpl: Deleting all users");
        userRepository.deleteAll();
    }

    @Override
    public long count() {
        logger.trace("=====>>UserGuiServiceImpl: Counting users");
        return userRepository.count();
    }

    @Override
    public User findEldest() {
        logger.trace("=====>>UserGuiServiceImpl: Searching for the eldest user in the database");
        Optional<User> result = userRepository.findFirstByOrderByBirthDateAsc();
        return result.get();
    }

    @Override
    public List<User> findAllByLastName(String lastName) {
        // capitalize first letter
        logger.trace("=====>>UserGuiServiceImpl: Searching for user using last name");
        List<User> result = userRepository.findAllByLastName(StringUtils.capitalize(lastName));
        logger.info("=====>>UserGuiServiceImpl: User created - found using last name");
        return result;
    }
}
