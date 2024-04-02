package com.example.weatherinfo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
@RestController
public class DefaultException extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultException.class);

	@ExceptionHandler({ ValidationException.class })
	protected ResponseEntity<Object> handleBusinessValidation(ValidationException be, WebRequest request) {
		try {
			ApiError apiError = null;
			if (Objects.nonNull(be.getErrors()) && !be.getErrors().isEmpty()) {
				for (String err : be.getErrors()) {
					apiError = new ApiError(HttpStatus.BAD_REQUEST);
					apiError.addSubError(err);
				}
			} else {
				apiError = new ApiError(HttpStatus.BAD_REQUEST, be);
			}
			return buildResponseEntity(apiError);
		} catch (Exception e) {

			return internalServerErrorEntity(e);
		}
	}

	private ResponseEntity<Object> internalServerErrorEntity(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
				String.format("Unable to perform given operation, please try after sometime."));
		return buildResponseEntity(apiError);
	}

	private final ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) {
		LOGGER.error(null, ex);
		if(request != null) {
			LOGGER.error(ex.getMessage() + "::" + request.getContextPath()+"::"+request.getDescription(true));
		}
		if(ex.getCause() != null){
			LOGGER.error("default_exception_cause:::"+ex.getCause().getMessage());
		}
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Internal ERROR", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
}