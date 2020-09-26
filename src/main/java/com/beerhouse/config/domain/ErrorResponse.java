package com.beerhouse.config.domain;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private final String message;
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final List<FieldError> fieldErrors;

    public ErrorResponse(String message, HttpStatus httpStatus, List<FieldError> fieldErrors) {
        this.message = message;
        timestamp = LocalDateTime.now();
        error = httpStatus.name();
        status = httpStatus.value();
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}
