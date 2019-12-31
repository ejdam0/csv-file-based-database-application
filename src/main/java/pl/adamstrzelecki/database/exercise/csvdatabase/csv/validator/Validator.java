package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

public interface Validator {

	<T> boolean checkIfDataFulfillsConditions(T t);
}
