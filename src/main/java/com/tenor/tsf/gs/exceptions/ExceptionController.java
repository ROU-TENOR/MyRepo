package com.tenor.tsf.gs.exceptions;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Error> handleRunTimeException(RuntimeException e) {
    	Error error=new Error(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@ExceptionHandler({SQLException.class})
	public ResponseEntity<Error> handleSQLException(SQLException e) {
		Error error=new Error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,LocalDateTime.now());
		return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Error> handleNPException(NullPointerException e) {
    	Error error=new Error(e.getMessage(), HttpStatus.BAD_REQUEST,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Error> handleIllegaleArgException(IllegalArgumentException e) {
    	Error error=new Error(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Error> handleNotFoundException(NotFoundException e) {
    	Error error=new Error(e.getMsg(), HttpStatus.NOT_FOUND,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({RequiredFieldException.class})
    public ResponseEntity<Error> handleRequiredFieldException(RequiredFieldException e) {
    	Error error=new Error(e.getMsg(), HttpStatus.BAD_REQUEST,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler({AllreadyReservedException.class})
    public ResponseEntity<Error> handleAllreadyReservedException(AllreadyReservedException e) {
    	Error error=new Error(e.getMsg(), HttpStatus.BAD_REQUEST,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({DateExpection.class})
    public ResponseEntity<Error> handleDateException(DateExpection e) {
    	Error error=new Error(e.getMessage(), HttpStatus.BAD_REQUEST,LocalDateTime.now());
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

	
}
