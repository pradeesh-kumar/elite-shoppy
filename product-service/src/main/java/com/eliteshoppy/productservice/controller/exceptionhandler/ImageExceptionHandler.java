package com.eliteshoppy.productservice.controller.exceptionhandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eliteshoppy.eliteshoppycommons.exception.EliteShoppyException;
import com.eliteshoppy.eliteshoppycommons.httpresponse.ErrorResponse;
import com.eliteshoppy.productservice.exception.FileStorageException;
import com.eliteshoppy.productservice.exception.ImageNotFoundException;

@ControllerAdvice
public class ImageExceptionHandler {

	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<ErrorResponse> notFound(ImageNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<ErrorResponse> storageException(FileStorageException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EliteShoppyException.class)
	public ResponseEntity<ErrorResponse> eliteShoppyException(EliteShoppyException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
