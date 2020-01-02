package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import java.util.List;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

public interface CsvFileReaderService {
	List<User> readCsvFile(String filename);
}
