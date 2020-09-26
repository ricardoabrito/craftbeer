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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerServiceImplTest {

    @Autowired
    private BeerServiceImpl beerService;

    private Long beerId;

    @Test @Order(1)
    public void shouldCreateBeer() {
        //given
        Beer beer = new Beer(null, "a", "a", "a", BigDecimal.ONE, "a");
        //when
        beer = beerService.save(beer);
        //then
        assertNotNull(beer.getId());
        this.beerId = beer.getId();
    }

    @Test @Order(2)
    public void shouldFindAllBeers() {
        //when
        List<Beer> beers = beerService.findAll();
        //then
        assertTrue(beers.size() > 0);
    }

    @Test @Order(3)
    public void shouldFindById() {
        //given
        Long idBeer = this.beerId;
        //when
        Beer beer = beerService.findById(idBeer);
        //then
        assertNotNull(beer);
        assertEquals(idBeer, beer.getId());
    }

    @Test @Order(4)
    public void shouldThrowExcptionIfBeerDontExists() {
        //given
        Long idBeer = Long.MAX_VALUE;
        //when
        assertThrows(ResourceNotFoundException.class, () -> beerService.findById(idBeer));
    }

    @Test @Order(5)
    public void shouldSaveChanges() {
        //given
        Long beerId = this.beerId;
        Beer beerToSave = new Beer(beerId, "b", "b", "b", BigDecimal.TEN.setScale(2), "b");
        //when
        beerService.save(beerToSave);
        //then
        Beer currentBeer = beerService.findById(beerId);
        assertEquals(beerToSave, currentBeer);
    }

    @Test @Order(6)
    public void shouldDelete() {
        //given
        Long beerId = this.beerId;
        //when
        beerService.delete(beerId);
        //then
        assertThrows(ResourceNotFoundException.class, () -> beerService.findById(beerId));
    }

    @Test @Order(7)
    public void shouldThrowExceptionWhenDeletedResourceDontExists() {
        //given
        Long beerId = this.beerId;
        //when
        assertThrows(ResourceNotFoundException.class, () -> beerService.delete(beerId));
    }

}
