package com.eliteshoppy.authservice.exception;

public class UserAccountAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAccountAlreadyExistsException() {
		super();
	}

	public UserAccountAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserAccountAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAccountAlreadyExistsException(String message) {
		super(message);
	}

	public UserAccountAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
