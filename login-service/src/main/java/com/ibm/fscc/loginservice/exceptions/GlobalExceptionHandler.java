package com.ibm.fscc.loginservice.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Adapted from code by anuragdhunna
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("UNAUTHORIZED");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
}
