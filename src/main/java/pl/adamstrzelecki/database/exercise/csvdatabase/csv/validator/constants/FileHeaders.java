package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator.constants;

public final class FileHeaders {

	// headers of the csv file
	private static final String[] HEADERS = { "first_name", "last_name", "birth_date", "phone_no" };

	private static final String USER_FNAME = "first_name";
	private static final String USER_LNAME = "last_name";
	private static final String USER_BDATE = "birth_date";
	private static final String USER_PHONENO = "phone_no";

	public static String[] getHeaders() {
		return HEADERS;
	}

	public static String getUserFname() {
		return USER_FNAME;
	}

	public static String getUserLname() {
		return USER_LNAME;
	}

	public static String getUserBdate() {
		return USER_BDATE;
	}

	public static String getUserPhoneno() {
		return USER_PHONENO;
	}

}
