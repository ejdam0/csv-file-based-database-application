package pl.adamstrzelecki.database.exercise.csvdatabase.csv.implementation;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.CsvFileReaderService;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.DataToTheListAdder;
import pl.adamstrzelecki.database.exercise.csvdatabase.csv.MyCSVFormat;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvFileReaderServiceImpl implements CsvFileReaderService {

    static final Logger logger = LoggerFactory.getLogger(CsvFileReaderServiceImpl.class);
    private DataToTheListAdder dataToTheListAdder;

    @Autowired
    public CsvFileReaderServiceImpl(DataToTheListAdder dataToTheListAdder) {
        this.dataToTheListAdder = dataToTheListAdder;
    }

    @Override
    public List<User> readCsvFile(String data) {
        CSVParser csvFileParser = null;
        MyCSVFormat csvFormat = new MyCSVFormat();

        // create a list of users to be filled by CSV file data
        logger.trace("=====>>CsvFileReaderService: Creating empty list of users");
        List<User> users = new ArrayList<>();

        try {
            // initialize StringReader object
            StringReader reader = new StringReader(data);

            // initialize CSVParser object
            csvFileParser = new CSVParser(reader, csvFormat.getCsvFileFormat());

            // get a list of CSV file records
            logger.trace("=====>>CsvFileReaderService: Creating and filling the list of csvRecords");
            List<CSVRecord> csvRecords = csvFileParser.getRecords();

            // save users to the list
            logger.trace("=====>>CsvFileReaderService: Filling the list of users.");
            dataToTheListAdder.addUserToTheList(users, csvRecords);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert csvFileParser != null;
                csvFileParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}
