package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

public interface DuplicatePhoneNoFinder {
    boolean searchForDuplicatePhoneNumbers(List<User> uploadedUsers, List<User> databaseUsers);
}
