package com.cooksys.secondassessment.exception;

public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException() {
		super("The entity could not be found.");
	}
}
