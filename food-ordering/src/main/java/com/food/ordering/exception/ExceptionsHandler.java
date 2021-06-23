package com.food.ordering.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private ErrorMessage notFoundHandler(NotFoundException ex) {
		
		ErrorMessage error = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return error;
	}

	@ExceptionHandler(UnprocessableEntityException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	private ErrorMessage unprocessableEntityHandler(UnprocessableEntityException ex) {
		
		ErrorMessage error = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
		return error;
	}
}
