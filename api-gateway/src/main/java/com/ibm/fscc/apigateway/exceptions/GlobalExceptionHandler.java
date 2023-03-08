package com.ibm.fscc.apigateway.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Adapted from code by anuragdhunna
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resourceNotFoundException(ResourceNotFoundException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("NOT_FOUND");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("CONFLICT");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponse> customException(CustomException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("BAD_REQUEST");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("UNAUTHORIZED");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
}
