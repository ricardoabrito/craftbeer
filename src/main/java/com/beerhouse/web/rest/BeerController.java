package com.beerhouse.web.rest;

import com.beerhouse.domain.Beer;
import com.beerhouse.service.BeerService;
import com.beerhouse.web.rest.vm.BeerVM;
import com.beerhouse.service.mapper.BeerMapper;
import com.beerhouse.web.rest.error.InvalidHttpVerbAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/beers")
public class BeerController {

    private final BeerService beerService;
    private final BeerMapper beerMapper;

    public BeerController(BeerService beerService, BeerMapper beerMapper) {
        this.beerService = beerService;
        this.beerMapper = beerMapper;
    }

    @GetMapping
    public List<BeerVM> getBeers() {
        return beerService.findAll().stream()
                .map(beerMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BeerVM getBeersById(@PathVariable Long id) {
        Beer beer = beerService.findById(id);
        return beerMapper.map(beer);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerVM createBeers(@Valid @RequestBody BeerVM beerVM) {
        if (beerVM.getId() != null) {
            throw new InvalidHttpVerbAPIException("Não é possível criar recurso com o id já informado!");
        }
        Beer beer = beerMapper.map(beerVM);
        return beerMapper.map(beerService.save(beer));
    }

    @PutMapping("/{id}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseEntity updateBeers(@PathVariable Long id, @Valid @RequestBody BeerVM beerVM) {
        Optional<Beer> currentBeerOptional = beerService.findByIdOptional(id);
        beerVM.setId(id);
        Beer beerRequest = beerMapper.map(beerVM);
        if (currentBeerOptional.isPresent()) {
            Beer currentBeer = currentBeerOptional.get();
            beerService.save(currentBeer.update(beerRequest));
            return new ResponseEntity(HttpStatus.OK);
        } else {
            beerService.save(beerRequest);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void patchBeers(@PathVariable Long id, @RequestBody BeerVM beerVM) {
        Beer currentBeer = beerService.findById(id);
        Beer updatedBeer = currentBeer.patch(beerMapper.map(beerVM));
        updatedBeer = beerService.save(updatedBeer);
        beerMapper.map(updatedBeer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeers(@PathVariable Long id) {
        beerService.delete(id);
    }

}
