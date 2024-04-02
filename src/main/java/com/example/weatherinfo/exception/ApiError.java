package com.example.weatherinfo.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class ApiError {

	@JsonIgnore
	private HttpStatus httpStatus;

	@JsonInclude(value = Include.NON_EMPTY)
	@JsonProperty("Error")
	private Set<String> subErrors;

	public ApiError() {

	}

	public ApiError(HttpStatus badRequestStatus, List<ObjectError> fieldErrors) {
		this(badRequestStatus);
		addValidationErrors(fieldErrors);
	}

	public ApiError(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ApiError(HttpStatus notFoundStatus, String errorMessage, String requestId) {
		this(notFoundStatus);
		addSubError(errorMessage);
	}

	public ApiError(HttpStatus badRequest, String errorMessage) {
		this(badRequest);
		addSubError(errorMessage);
	}

	public ApiError(HttpStatus badRequest, CustomException be) {
		this(badRequest);
		addSubError(be.getLocalizedMessage());
	}

	public ApiError(HttpStatus badRequest, FieldError fieldError) {
		this(badRequest);
		addValidationError(fieldError);
	}

	public ApiError(int status, Map<String, Object> errorAttributes) {
		this(HttpStatus.valueOf(status));
		addSubError(String.format("%s %s", errorAttributes.get("path"), errorAttributes.get("error")));
	}

	public void addSubError(String subError) {
		if (Objects.isNull(this.subErrors)) {
			this.subErrors = new HashSet<>();
		}
		subErrors.add(subError);
	}

	public Set<String> getSubErrors() {
		return subErrors;
	}

	private void addValidationError(String message) {
		addSubError(message);
	}

	private void addValidationError(ObjectError fieldError) {
		this.addValidationError(fieldError.getDefaultMessage());
	}

	private void addValidationErrors(List<ObjectError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}

	public ApiError(HttpStatus badRequest, ValidationException be) {
		this(badRequest);
		addSubError(be.getLocalizedMessage());
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
