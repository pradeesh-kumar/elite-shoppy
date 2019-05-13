package com.eliteshoppy.eliteshoppycommons.exception;

public class EliteShoppyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EliteShoppyException() {
		super();
	}

	public EliteShoppyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EliteShoppyException(String message, Throwable cause) {
		super(message, cause);
	}

	public EliteShoppyException(String message) {
		super(message);
	}

	public EliteShoppyException(Throwable cause) {
		super(cause);
	}

}
