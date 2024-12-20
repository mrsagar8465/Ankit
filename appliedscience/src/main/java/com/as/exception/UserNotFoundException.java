package com.as.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message)
	{
		super(message);
	}
}
