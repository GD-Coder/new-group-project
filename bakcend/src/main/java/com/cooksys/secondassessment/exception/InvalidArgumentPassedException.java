package com.cooksys.secondassessment.exception;

public class InvalidArgumentPassedException extends RuntimeException {
	
	public InvalidArgumentPassedException() {
		super("An invalid argument was passed in.");
	}
	
}
