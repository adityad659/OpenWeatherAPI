package com.example.weatherinfo.exception;


public abstract class EndUserException extends RuntimeException {

	private static final long serialVersionUID = -8223181116316721339L;

	public EndUserException(String msg) {
		super(msg);
	}

	public EndUserException(Throwable t) {
		super(t);
	}
}
