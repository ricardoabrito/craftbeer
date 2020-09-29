package com.beerhouse.service.impl;

import com.beerhouse.domain.Beer;
import com.beerhouse.web.rest.error.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerServiceImplTest {

    @Autowired
    private BeerServiceImpl beerService;

    @Test
    public void shouldCreateBeer() {
        //given
        Beer beer = new Beer(null, "a", "a", "a", BigDecimal.ONE, "a");
        //when
        Beer savedBeer = beerService.save(beer);
        //then
        assertNotNull(savedBeer.getId());
        assertEquals(beer.getName(), savedBeer.getName());
        assertEquals(beer.getCategory(), savedBeer.getCategory());
        assertEquals(beer.getPrice(), savedBeer.getPrice());
        assertEquals(beer.getAlcoholContent(), savedBeer.getAlcoholContent());
        assertEquals(beer.getIngredients(), savedBeer.getIngredients());
    }

    @Test
    public void shouldFindAllBeers() {
        //given
        Beer beer = new Beer(null, "a", "a", "a", BigDecimal.ONE, "a");
        beerService.save(beer);
        //when
        List<Beer> beers = beerService.findAll();
        //then
        assertTrue(beers.size() > 0);
    }

    @Test
    public void shouldFindById() {
        //given
        Beer beer = new Beer(null, "a", "a", "a", BigDecimal.ONE.setScale(2), "a");
        beer = beerService.save(beer);
        //when
        Beer findBeer = beerService.findById(beer.getId());
        //then
        assertNotNull(findBeer);
        assertEquals(beer, findBeer);
    }

    @Test
    public void shouldThrowExcptionIfBeerDontExists() {
        //given
        Long idBeer = Long.MAX_VALUE;
        //when
        assertThrows(ResourceNotFoundException.class, () -> beerService.findById(idBeer));
    }

    @Test
    public void shouldSaveChanges() {
        //given
        Beer beer = new Beer(null, "a", "a", "a", BigDecimal.ONE, "a");
        Long beerId = beerService.save(beer).getId();
        Beer beerToSave = new Beer(beerId, "b", "b", "b", BigDecimal.TEN.setScale(2), "b");
        //when
        Beer savedBeer = beerService.save(beerToSave);
        //then
        Beer currentBeer = beerService.findById(beerId);
        assertEquals(savedBeer, currentBeer);
    }

    @Test
    public void shouldDelete() {
        //given
        Beer beer = new Beer(null, "a", "a", "a", BigDecimal.ONE, "a");
        Long beerId = beerService.save(beer).getId();
        //when
        beerService.delete(beerId);
        //then
        assertThrows(ResourceNotFoundException.class, () -> beerService.findById(beerId));
    }

    @Test
    public void shouldThrowExceptionWhenDeletedResourceDontExists() {
        //given
        Long beerId = Long.MAX_VALUE;
        //when
        assertThrows(ResourceNotFoundException.class, () -> beerService.delete(beerId));
    }

}
