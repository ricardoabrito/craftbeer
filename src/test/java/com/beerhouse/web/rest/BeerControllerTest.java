package com.beerhouse.web.rest;

import com.beerhouse.web.rest.vm.BeerVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.List;

import static com.beerhouse.util.BeerVMFactory.createBeerVMToPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void shouldCreateBeerOnPost() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        //when
        ResponseEntity<BeerVM> createdEntity = this.restTemplate.postForEntity(resource(), beerVM, BeerVM.class);
        //then
        assertEquals(createdEntity.getStatusCode(), HttpStatus.CREATED);
        BeerVM createdBeer = createdEntity.getBody();
        assertNotNull(createdBeer.getId());
        beerVM.setId(createdBeer.getId());
        assertEquals(beerVM, createdBeer);
    }

    @Test
    public void shouldThrowBadRequestIfIdIsOnCreateBeerOnPost() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        beerVM.setId(1l);
        //when
        ResponseEntity<BeerVM> createdEntity = this.restTemplate.postForEntity(resource(), beerVM, BeerVM.class);
        //then
        assertEquals(createdEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldThrowBadRequestIfAnyNonNullFildIsNullOnCreateBeerOnPost() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        beerVM.setName(null);
        //when
        ResponseEntity<BeerVM> createdEntity = this.restTemplate.postForEntity(resource(), beerVM, BeerVM.class);
        //then
        assertEquals(createdEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldFindAllOnGet() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        BeerVM createdBeer = this.restTemplate.postForObject(resource(), beerVM, BeerVM.class);
        //when
        ResponseEntity<List> findAllEntity = this.restTemplate.getForEntity(resource(), List.class);
        //then
        assertEquals(findAllEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(findAllEntity.getBody().size() > 0);
    }

    @Test
    public void shouldFindByIdOnGet() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        BeerVM createdBeer = this.restTemplate.postForObject(resource(), beerVM, BeerVM.class);
        Long beerId = createdBeer.getId();
        //when
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerId, BeerVM.class);
        //then
        assertEquals(findEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(beerId, findEntity.getBody().getId());
    }

    @Test
    public void shouldThrowExptionWhenFindByIdDontExistsOnGet() {
        //given
        Long beerId = Long.MAX_VALUE;
        //when
        ResponseEntity<Object> errorEntitty = this.restTemplate.getForEntity(resource() + "/" + beerId, Object.class);
        //then
        assertEquals(errorEntitty.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldUpdateOnPut() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        BeerVM beerToUpdate = this.restTemplate.postForObject(resource(), beerVM, BeerVM.class);
        beerToUpdate.setName("b");
        HttpEntity<BeerVM> httpEntity = new HttpEntity(beerToUpdate);
        //when
        ResponseEntity putEntity = this.restTemplate.exchange(resource() + "/" + beerToUpdate.getId(), HttpMethod.PUT, httpEntity, Object.class);
        //then
        assertEquals(HttpStatus.OK, putEntity.getStatusCode());
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerToUpdate.getId(), BeerVM.class);
        assertEquals(beerToUpdate, findEntity.getBody());
    }

    @Test
    public void shouldCreateOnPut() {
        //given
        Long beerId = 42l;
        BeerVM beerVM = createBeerVMToPost();
        beerVM.setName("b");
        beerVM.setId(beerId);
        HttpEntity<BeerVM> httpEntity = new HttpEntity(beerVM);
        //when
        ResponseEntity putEntity = this.restTemplate.exchange(resource() + "/" + beerId, HttpMethod.PUT, httpEntity, Object.class);
        //then
        assertEquals(HttpStatus.CREATED, putEntity.getStatusCode());
    }

    @Test
    public void shouldPatchOnPatch() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        BeerVM beerToPatch = this.restTemplate.postForObject(resource(), beerVM, BeerVM.class);
        beerToPatch.setName("c");
        //when
        this.restTemplate.patchForObject(resource() + "/" + beerToPatch.getId(), beerToPatch, Object.class);
        //then
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerToPatch.getId(), BeerVM.class);
        assertEquals(beerToPatch, findEntity.getBody());
    }

    @Test
    public void shouldDelete() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        BeerVM beerToDelete = this.restTemplate.postForObject(resource(), beerVM, BeerVM.class);
        //when
        this.restTemplate.delete(resource() + "/" + beerToDelete.getId());
        //then
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerToDelete.getId(), BeerVM.class);
        assertEquals(HttpStatus.BAD_REQUEST, findEntity.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenDeletedResourceDontExists() {
        ResponseEntity deleteEntity = this.restTemplate.exchange(resource() + "/" + Long.MAX_VALUE, HttpMethod.DELETE, null, Object.class);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, deleteEntity.getStatusCode());
    }

    private String resource() {
        return "http://localhost:" + port + "/beerhouse/beers";
    }


}
