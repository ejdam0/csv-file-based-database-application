package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

public interface SingleUserDuplicatePhoneNoFinder {
    boolean searchForDuplicates(List<User> databaseUsers, User user);
}
