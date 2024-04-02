package com.example.weatherinfo.exception;

import java.util.List;

public class CustomException extends EndUserException {

	private static final long serialVersionUID = -6035583940551573182L;
	private List<String> errors;

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Exception e) {
		super(e);
	}

	public CustomException(List<String> errors) {
		super("Multiple errors");
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

}
