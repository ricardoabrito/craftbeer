package com.beerhouse.web.rest.error;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BeerHouseAPIException {

    public ResourceNotFoundException(Long id) {
        super(String.format("Não existe recurso com o id informado %d.", id), HttpStatus.BAD_REQUEST);
    }

}
