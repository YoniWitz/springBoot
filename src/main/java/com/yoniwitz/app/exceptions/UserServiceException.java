package com.yoniwitz.app.exceptions;

public class UserServiceException extends RuntimeException {
	private static final long serialVersionUID = 8395984607174498367L;

	public UserServiceException(String message) {
		super(message);
	}

}
