package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import java.util.List;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

public interface CsvFileReaderService {

	public List<User> readCsvFile(String filename);
}
