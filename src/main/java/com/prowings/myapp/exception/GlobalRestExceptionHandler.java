package com.prowings.myapp.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.prowings.myapp.model.MyCustomError;

@ControllerAdvice
public class GlobalRestExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<MyCustomError> handleSQLException(HttpServletRequest request, Exception ex) {
		System.out.println("inside NotFoundException handler method");

		MyCustomError error = new MyCustomError();
		error.setMessage(ex.getMessage());
		// error.setRootCause("abc");
		error.setStatusCode(404);

		return new ResponseEntity<MyCustomError>(error, HttpStatus.NOT_FOUND);

	}
}
