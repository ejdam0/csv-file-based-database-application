package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

public interface DataToTheListAdder {

	void addUserToTheList(List<User> users, List<CSVRecord> csvRecords);
}
