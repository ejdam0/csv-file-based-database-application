package pl.adamstrzelecki.database.exercise.csvdatabase.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

public interface UserService {

	public Page<User> findAll(Pageable pageable);

	public User findById(int theId);

	public void save(User user);

	public void deleteById(int theId);

	public void deleteAll();

	public long count();

	public User findEldest();

	public List<User> findAllByLastName(String lastName);
}
