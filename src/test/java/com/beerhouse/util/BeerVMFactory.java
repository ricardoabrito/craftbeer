package com.beerhouse.util;

import com.beerhouse.web.rest.vm.BeerVM;

import java.math.BigDecimal;

public class BeerVMFactory {

    private static BeerVM createBeerVM() {
        BeerVM beerVM = new BeerVM();
        beerVM.setAlcoholContent("a");
        beerVM.setIngredients("a");
        beerVM.setCategory("a");
        beerVM.setName("a");
        beerVM.setPrice(BigDecimal.ONE);
        return beerVM;
    }

    public static BeerVM createBeerVMToMap() {
        BeerVM beerVM = createBeerVM();
        beerVM.setId(1l);
        return beerVM;
    }

    public static BeerVM createBeerVMToPost() {
        return createBeerVM();
    }

}
