package com.beerhouse.web.rest.error;

import org.springframework.http.HttpStatus;

public abstract class BeerHouseAPIException extends RuntimeException {

    private final HttpStatus status;

    public BeerHouseAPIException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BeerHouseAPIException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
