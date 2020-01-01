package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.csv;

import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

public interface GuiCsvFileReaderService {

	List<User> readCsvFile(String filename);
}
