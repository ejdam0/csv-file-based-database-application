package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants;

public final class RegularExpressions {

	private static final String LETTERS_REGEX = "\\p{L}+";
	private static final String NUMBERS_REGEX = "\\d{9}";
	private static final String DATE_REGEX = "\\d{4}.\\d{1,2}.\\d{1,2}";

	public static String getLettersRegex() {
		return LETTERS_REGEX;
	}

	public static String getNumbersRegex() {
		return NUMBERS_REGEX;
	}

	public static String getDateRegex() {
		return DATE_REGEX;
	}

}
