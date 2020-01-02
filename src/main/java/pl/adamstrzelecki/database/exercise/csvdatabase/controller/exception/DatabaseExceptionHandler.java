package pl.adamstrzelecki.database.exercise.csvdatabase.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {

    static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);

    // add an exception handler for UserNotFoundException
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {

        // create UserErrorResponse
        GeneralDBErrorResponse error = new UserErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity
        logger.error("=====>>DatabaseExceptionHandler: User not found.");
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    // add an exception handler for DuplicatePhoneNoFoundException
    @ExceptionHandler
    public ResponseEntity<PhoneNoErrorResponse> handleException(DuplicatePhoneNoFoundException e) {

        // create PhoneNoErrorResponse
        GeneralDBErrorResponse error = new PhoneNoErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity
        logger.error("=====>>DatabaseExceptionHandler: Exception: Phone number duplicate found.");
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }

    // add an exception handler for NoUserMetConditionsEcxception
    @ExceptionHandler
    public ResponseEntity<NoUserMetConditionsErrorResponse> handleException(NoUserMetConditionsException e) {

        // create UserErrorResponse
        GeneralDBErrorResponse error = new NoUserMetConditionsErrorResponse(HttpStatus.CONFLICT.value(),
                e.getMessage(), System.currentTimeMillis());

        // return ResponseEntity
        logger.error("=====>>DatabaseExceptionHandler: No user has met the requirements.");
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }

    // add another exception handler -> to catch any exception (catch all)
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception e) {

        // create UserErrorResponse
        // bad_request -> 400 error
        GeneralDBErrorResponse error = new UserErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity
        logger.error("=====>>DatabaseExceptionHandler: Exception");
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
