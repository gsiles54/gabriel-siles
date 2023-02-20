package com.xcale.challenge.exception;

public class UserAlredyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlredyExistsException(String msj) {
		super(msj);
	}
}
