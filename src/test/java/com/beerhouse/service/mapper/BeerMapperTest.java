package com.beerhouse.service.mapper;

import com.beerhouse.domain.Beer;
import com.beerhouse.web.rest.vm.BeerVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.beerhouse.util.BeerVMFactory.createBeerVMToMap;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerMapperTest {

    @Autowired
    private BeerMapper beerMapper;

    @Test
    void shouldMapToVM() {
        //given
        Beer beer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        //when
        BeerVM beerVM = beerMapper.map(beer);
        //then
        assertEquals(beer.getId(), beerVM.getId());
        assertEquals(beer.getName(), beerVM.getName());
        assertEquals(beer.getIngredients(), beerVM.getIngredients());
        assertEquals(beer.getAlcoholContent(), beerVM.getAlcoholContent());
        assertEquals(beer.getPrice(), beerVM.getPrice());
        assertEquals(beer.getCategory(), beerVM.getCategory());

    }

    @Test
    void shouldMapToDomain() {
        //given
        BeerVM beerVM = createBeerVMToMap();
        //when
        Beer beer = beerMapper.map(beerVM);
        //then
        assertEquals(beerVM.getId(), beer.getId());
        assertEquals(beerVM.getName(), beer.getName());
        assertEquals(beerVM.getIngredients(), beer.getIngredients());
        assertEquals(beerVM.getAlcoholContent(), beer.getAlcoholContent());
        assertEquals(beerVM.getPrice(), beer.getPrice());
        assertEquals(beerVM.getCategory(), beer.getCategory());
    }


}
