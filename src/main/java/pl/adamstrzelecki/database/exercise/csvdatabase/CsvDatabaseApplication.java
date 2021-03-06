package pl.adamstrzelecki.database.exercise.csvdatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("pl.adamstrzelecki.database.exercise.csvdatabase.entity")
public class CsvDatabaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(CsvDatabaseApplication.class, args);
    }
}