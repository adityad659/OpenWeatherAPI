package com.example.weatherinfo.exception;

import java.util.List;

public class ValidationException extends EndUserException {

	private static final long serialVersionUID = 3605124263023931528L;

	private List<String> errors;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Exception e) {
		super(e);
	}

	public ValidationException(List<String> errors) {
		super("Multiple errors");
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

}
