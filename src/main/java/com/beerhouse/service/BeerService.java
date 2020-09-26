package com.beerhouse.service;

import com.beerhouse.domain.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerService {

    List<Beer> findAll();

    Beer findById(Long id);

    Optional<Beer> findByIdOptional(Long id);

    Beer save(Beer beer);

    void delete(Long id);

}
