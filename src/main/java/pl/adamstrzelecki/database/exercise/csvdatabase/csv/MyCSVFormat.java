package pl.adamstrzelecki.database.exercise.csvdatabase.csv;

import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Component;

import pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants.FileHeaders;

@Component
public class MyCSVFormat {
	
	// CSVFormat object with the header mapping
	private CSVFormat csvFileFormat;
	
	public MyCSVFormat() {
		this.csvFileFormat = CSVFormat.EXCEL
				.withDelimiter(';')
				.withIgnoreSurroundingSpaces()
				.withNullString("")
				.withIgnoreEmptyLines(true)
				.withHeader(FileHeaders.getHeaders());
	}

	public CSVFormat getCsvFileFormat() {
		return csvFileFormat;
	}
}
