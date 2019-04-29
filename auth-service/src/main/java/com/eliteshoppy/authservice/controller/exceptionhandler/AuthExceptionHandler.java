package com.eliteshoppy.authservice.controller.exceptionhandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eliteshoppy.authservice.exception.UserAccountAlreadyExistsException;
import com.eliteshoppy.authservice.exception.UserNotFoundException;
import com.eliteshoppy.eliteshoppycommons.httpresponse.ErrorResponse;

@ControllerAdvice
public class AuthExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> notFound(UserNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAccountAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> notFound(UserAccountAlreadyExistsException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
