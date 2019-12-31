package pl.adamstrzelecki.database.exercise.csvdatabase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// add a method to sort by birth date
	Page<User> findAllByOrderByBirthDateAsc(Pageable pageable);

	// add a method to find users by their last_name;
	List<User> findAllByLastName(String lastName);

	// find eldest user
	Optional<User> findFirstByOrderByBirthDateAsc();
}
