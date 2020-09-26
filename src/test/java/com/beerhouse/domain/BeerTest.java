package com.beerhouse.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BeerTest {

    @Test
    void shouldUpdateBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toUpdate = new Beer(2l, "b", "b", "b", BigDecimal.TEN, "b");
        //when
        Beer updatedBeer = aBeer.update(toUpdate);
        //then
        Beer expectedState = new Beer(1l, "b", "b", "b", BigDecimal.TEN, "b");
        assertEquals(expectedState, updatedBeer);
    }

    @Test
    void shouldPatchMultipleValuesBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toPatch = new Beer(2l, "b", null, "b", BigDecimal.TEN, null);
        //when
        Beer patchedBeer = aBeer.patch(toPatch);
        //then
        Beer expectedState = new Beer(1l, "b", "a", "b", BigDecimal.TEN, "a");
        assertEquals(expectedState, patchedBeer);
    }

    @Test
    void shouldPatchNameBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toPatch = new Beer(null, "b", null, null, null, null);
        //when
        Beer patchedBeer = aBeer.patch(toPatch);
        //then
        Beer expectedState = new Beer(1l, "b", "a", "a", BigDecimal.ONE, "a");
        assertEquals(expectedState, patchedBeer);
    }

    @Test
    void shouldPatchIngrientsBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toPatch = new Beer(null, null, "b", null, null, null);
        //when
        Beer patchedBeer = aBeer.patch(toPatch);
        //then
        Beer expectedState = new Beer(1l, "a", "b", "a", BigDecimal.ONE, "a");
        assertEquals(expectedState, patchedBeer);
    }

    @Test
    void shouldPatchAlcoholContentBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toPatch = new Beer(null, null, null, "b", null, null);
        //when
        Beer patchedBeer = aBeer.patch(toPatch);
        //then
        Beer expectedState = new Beer(1l, "a", "a", "b", BigDecimal.ONE, "a");
        assertEquals(expectedState, patchedBeer);
    }

    @Test
    void shouldPatchPriceBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toPatch = new Beer(null, null, null, null, BigDecimal.TEN, null);
        //when
        Beer patchedBeer = aBeer.patch(toPatch);
        //then
        Beer expectedState = new Beer(1l, "a", "a", "a", BigDecimal.TEN, "a");
        assertEquals(expectedState, patchedBeer);
    }

    @Test
    void shouldPatchCategoryBeer() {
        //given
        Beer aBeer = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "a");
        Beer toPatch = new Beer(null, null, null, null, null, "b");
        //when
        Beer patchedBeer = aBeer.patch(toPatch);
        //then
        Beer expectedState = new Beer(1l, "a", "a", "a", BigDecimal.ONE, "b");
        assertEquals(expectedState, patchedBeer);
    }



}
