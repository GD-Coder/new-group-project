package com.cooksys.secondassessment.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cooksys.secondassessment.exception.EntityNotFoundException;
import com.cooksys.secondassessment.exception.InvalidArgumentPassedException;
import com.cooksys.secondassessment.exception.UsernameExistsException;


@ControllerAdvice
public class SecondAssessmentHandler extends ResponseEntityExceptionHandler {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		log.warn(ex.getMessage());
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleInvalidArgumentPassedException(InvalidArgumentPassedException ex, WebRequest request) {
		log.warn(ex.getMessage());
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleUsernameExistsException(UsernameExistsException ex, WebRequest request) {
		log.warn(ex.getMessage());
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.IM_USED, request);
	}
	
}
