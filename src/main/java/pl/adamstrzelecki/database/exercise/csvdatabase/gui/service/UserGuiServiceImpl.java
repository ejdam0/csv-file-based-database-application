package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.SingleUserDuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.Validator;
import pl.adamstrzelecki.database.exercise.csvdatabase.dao.UserRepository;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class UserGuiServiceImpl implements UserGuiService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private Validator userValidator;

    @Autowired
    public UserGuiServiceImpl(UserRepository userRepository, Validator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public List<User> findAll() {
        logger.trace("=====>>UserServiceImpl: findAll()");
        return userRepository.findAll();
    }

    @Override
    public User findById(int theId) {
        logger.trace("=====>>UserServiceImpl: Searching for user using id");
        Optional<User> result = userRepository.findById(theId);
        return result.get();
    }

    @Override
    public boolean save(User user) {
        logger.trace("=====>>UserServiceImpl: Verifying the provided User data");
        if (!userValidator.checkIfDataFulfillsConditions(user)) {
            logger.trace("=====>>UserServiceImpl: Provided data is incorrect");
            return false;
        }

        logger.trace("=====>>UserServiceImpl: Data is correct");
        logger.trace("=====>>UserServiceImpl: Looking for duplicate phone numbers in the database");
        List<User> users = userRepository.findAll();
        if (SingleUserDuplicatePhoneNoFinder.searchForDuplicates(users, user)) {
            logger.trace("=====>>UserServiceImpl: Number already exists in the database");
            return false;
        }

        logger.trace("=====>>UserServiceImpl: User data correct. Saving new user");
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteById(int theId) {
        logger.trace("=====>>UserServiceImpl: Searching for user using id");
        Optional<User> result = userRepository.findById(theId);
        if (result.isEmpty()) {
            logger.error("=====>>UserServiceImpl: User not found");
            return false;
        } else {
            logger.trace("=====>>UserServiceImpl: Deleting user using id");
            userRepository.deleteById(theId);
        }
        return true;
    }

    @Override
    public void deleteAll() {
        logger.trace("=====>>UserServiceImpl: Deleting all users");
        userRepository.deleteAll();
    }

    @Override
    public long count() {
        logger.trace("=====>>UserServiceImpl: Counting users");
        return userRepository.count();
    }

    @Override
    public User findEldest() {
        logger.trace("=====>>UserServiceImpl: Searching for the eldest user in the database");
        Optional<User> result = userRepository.findFirstByOrderByBirthDateAsc();
        return result.get();
    }

    @Override
    public List<User> findAllByLastName(String lastName) {
        // capitalize first letter
        logger.trace("=====>>UserServiceImpl: Searching for user using last name");
        List<User> result = userRepository.findAllByLastName(StringUtils.capitalize(lastName));
        logger.info("=====>>UserServiceImpl: User created - found using last name");
        return result;
    }
}
