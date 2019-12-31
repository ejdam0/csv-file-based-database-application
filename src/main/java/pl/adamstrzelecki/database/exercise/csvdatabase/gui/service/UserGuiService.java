package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

public interface UserGuiService {

    List<User> findAll();

    User findById(int theId);

    boolean save(User user);

    boolean deleteById(int theId);

    void deleteAll();
    
    long count();

    User findEldest();

    List<User> findAllByLastName(String lastName);
}
