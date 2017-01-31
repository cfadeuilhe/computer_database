package com.excilys.computerdatabase.exceptions;

public class ValidationException extends RuntimeException{

	/**
     * 
     */
    private static final long serialVersionUID = -451898362823240654L;

    public ValidationException(String message) {
		super(message);
	}
}
