package pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.csv;

import org.apache.commons.csv.CSVRecord;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.util.List;

public interface GuiDataToTheListAdder {

    void addUserToTheList(List<User> users, List<CSVRecord> csvRecords);
}
