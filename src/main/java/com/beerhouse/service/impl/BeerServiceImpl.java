package com.beerhouse.service.impl;

import com.beerhouse.domain.Beer;
import com.beerhouse.repository.BeerRepository;
import com.beerhouse.service.BeerService;
import com.beerhouse.web.rest.error.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    public BeerServiceImpl(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Override
    public Beer findById(Long id) {
        Optional<Beer> optionalBeer = findByIdOptional(id);
        return optionalBeer.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Optional<Beer> findByIdOptional(Long id) {
        return beerRepository.findById(id);
    }

    @Override
    public Beer save(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public void delete(Long id) {
        try {
            beerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(id);
        }

    }
}
