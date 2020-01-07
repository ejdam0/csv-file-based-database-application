package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import org.apache.commons.csv.CSVFormat;

public class MyCSVFormat {

    private final String[] HEADERS = {"first_name", "last_name", "birth_date", "phone_no"};
    // CSVFormat object with the header mapping
    private CSVFormat csvFileFormat;

    public MyCSVFormat() {
        this.csvFileFormat = CSVFormat.EXCEL
                .withDelimiter(';')
                .withIgnoreSurroundingSpaces()
                .withNullString("")
                .withIgnoreEmptyLines(true)
                .withHeader(HEADERS);
    }

    public CSVFormat getCsvFileFormat() {
        return csvFileFormat;
    }
}
