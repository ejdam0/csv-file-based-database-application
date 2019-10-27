package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

public interface Validator {

	public <T> boolean checkIfDataFulfillsConditions(T t);
}
