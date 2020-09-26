package com.beerhouse.web.rest.error;

import org.springframework.http.HttpStatus;

public class InvalidHttpVerbAPIException extends BeerHouseAPIException {

    public InvalidHttpVerbAPIException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
