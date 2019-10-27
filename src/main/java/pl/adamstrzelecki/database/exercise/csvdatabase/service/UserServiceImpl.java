package pl.adamstrzelecki.database.exercise.csvdatabase.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception.DuplicatePhoneNoFoundException;
import pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception.NoUserMetConditionsException;
import pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception.UserNotFoundException;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.SingleUserDuplicatePhoneNoFinder;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.Validator;
import pl.adamstrzelecki.database.exercise.csvdatabase.dao.UserRepository;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

@Service
public class UserServiceImpl implements UserService {

	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;
	Validator userValidator;

	// inject the userRepository
	@Autowired
	public UserServiceImpl(UserRepository userRepository, Validator userValidator) {
		this.userRepository = userRepository;
		this.userValidator = userValidator;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {

		logger.trace("=====>>UserServiceImpl: findAll()");
		return userRepository.findAllByOrderByBirthDateAsc(pageable);
	}

	@Override
	public User findById(int theId) {

		logger.trace("=====>>UserServiceImpl: Searching for user using id");
		Optional<User> result = userRepository.findById(theId);

		User user = null;

		if (result.isPresent()) {
			logger.info("=====>>UserServiceImpl: User created - found using id");
			user = result.get();
		} else {
			logger.error("=====>>UserServiceImpl: User not found");
			throw new UserNotFoundException("User of id: " + theId + " not found.");
		}
		return user;
	}

	@Override
	public void save(User user) {

		logger.trace("=====>>UserServiceImpl: Verifying the provided User data");
		if(!userValidator.checkIfDataFulfillsConditions(user)) {
			logger.trace("=====>>UserServiceImpl: Provided data is incorrect");
			throw new NoUserMetConditionsException("The provided data is incorrect!");
		}

		logger.trace("=====>>UserServiceImpl: Data is correct");

		logger.trace("=====>>UserServiceImpl: Looking for duplicate phone numbers in the database");
		List<User> databaseUsers = userRepository.findAll();

		if (SingleUserDuplicatePhoneNoFinder.searchForDuplicates(databaseUsers, user)) {
			logger.trace("=====>>UserServiceImpl: Number already exists in the database");
			throw new DuplicatePhoneNoFoundException(
					"User with the same phone number (" + user.getPhoneNo() + ") already exists in the database");
		}

		logger.trace("=====>>UserServiceImpl: User data correct. Saving new user");
		userRepository.save(user);
	}

	@Override
	public void deleteById(int theId) {

		logger.trace("=====>>UserServiceImpl: Searching for user using id");
		Optional<User> result = userRepository.findById(theId);

		if (result == null) {
			logger.error("=====>>UserServiceImpl: User not found");
			throw new UserNotFoundException("User of id: " + theId + " not found.");
		} else {
			logger.trace("=====>>UserServiceImpl: Deleting user using id");
			userRepository.deleteById(theId);
		}
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

		User user = null;

		if (result.isPresent()) {
			logger.info("=====>>UserServiceImpl: User created - found using id");
			user = result.get();
		} else {
			logger.error("=====>>UserServiceImpl: User not found");
			throw new UserNotFoundException("No users in database!");
		}
		return user;
	}

	@Override
	public List<User> findAllByLastName(String lastName) {

		// capitalize first letter
		logger.trace("=====>>UserServiceImpl: Searching for user using last name");
		List<User> result = userRepository.findAllByLastName(StringUtils.capitalize(lastName));

		if (result.isEmpty()) {
			logger.error("=====>>UserServiceImpl: User not found");
			throw new UserNotFoundException("User with last name: " + lastName + " not found.");
		}
		logger.info("=====>>UserServiceImpl: User created - found using last name");
		return result;
	}

}
