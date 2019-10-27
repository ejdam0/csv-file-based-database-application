package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class DuplicatePhoneNoFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4987356142814563251L;

	public DuplicatePhoneNoFoundException() {

	}

	public DuplicatePhoneNoFoundException(String message) {
		super(message);

	}

	public DuplicatePhoneNoFoundException(Throwable cause) {
		super(cause);

	}

	public DuplicatePhoneNoFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public DuplicatePhoneNoFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
