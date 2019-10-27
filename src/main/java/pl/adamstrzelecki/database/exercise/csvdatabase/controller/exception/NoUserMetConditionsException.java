package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

public class NoUserMetConditionsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2136725693468736678L;

	public NoUserMetConditionsException() {

	}

	public NoUserMetConditionsException(String message) {
		super(message);

	}

	public NoUserMetConditionsException(Throwable cause) {
		super(cause);

	}

	public NoUserMetConditionsException(String message, Throwable cause) {
		super(message, cause);

	}

	public NoUserMetConditionsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
