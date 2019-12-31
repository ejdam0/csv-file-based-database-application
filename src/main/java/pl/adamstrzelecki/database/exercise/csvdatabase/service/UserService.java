package pl.adamstrzelecki.database.exercise.csvdatabase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

public interface UserService {

	Page<User> findAll(Pageable pageable);

	User findById(int theId);

	void save(User user);

	void deleteById(int theId);

	void deleteAll();

	long count();

	User findEldest();

	List<User> findAllByLastName(String lastName);
}
