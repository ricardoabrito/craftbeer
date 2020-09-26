package com.beerhouse.web.rest;

import com.beerhouse.web.rest.vm.BeerVM;
import org.junit.jupiter.api.*;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Long beerId;

    @BeforeAll
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test @Order(1)
    public void shouldCreateBeerOnPost() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        //when
        ResponseEntity<BeerVM> createdEntity = this.restTemplate.postForEntity(resource(), beerVM, BeerVM.class);
        //then
        assertEquals(createdEntity.getStatusCode(), HttpStatus.CREATED);
        BeerVM createdBeer = createdEntity.getBody();
        assertNotNull(createdBeer.getId());
        this.beerId = createdBeer.getId();
        beerVM.setId(this.beerId);
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

    @Test @Order(2)
    public void shouldFindAllOnGet() {
        //when
        ResponseEntity<List> findAllEntity = this.restTemplate.getForEntity(resource(), List.class);
        //then
        assertEquals(findAllEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(findAllEntity.getBody().size() > 0);
    }

    @Test @Order(3)
    public void shouldFindByIdOnGet() {
        //given
        Long beerId = this.beerId;
        //when
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerId, BeerVM.class);
        //then
        assertEquals(findEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(beerId, findEntity.getBody().getId());
    }

    @Test @Order(3)
    public void shouldThrowExptionWhenFindByIdDontExistsOnGet() {
        //given
        Long beerId = Long.MAX_VALUE;
        //when
        ResponseEntity<Object> errorEntitty = this.restTemplate.getForEntity(resource() + "/" + beerId, Object.class);
        //then
        assertEquals(errorEntitty.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test @Order(3)
    public void shouldUpdateOnPut() {
        //given
        BeerVM beerVM = createBeerVMToPost();
        beerVM.setName("b");
        beerVM.setId(beerId);
        HttpEntity<BeerVM> httpEntity = new HttpEntity(beerVM);
        //when
        ResponseEntity putEntity = this.restTemplate.exchange(resource() + "/" + beerId, HttpMethod.PUT, httpEntity, Object.class);
        //then
        assertEquals(HttpStatus.OK, putEntity.getStatusCode());
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerId, BeerVM.class);
        assertEquals(beerVM.getName(), findEntity.getBody().getName());
    }

    @Test @Order(3)
    public void shouldCreateOnPut() {
        //given
        Long beerId = 2l;
        BeerVM beerVM = createBeerVMToPost();
        beerVM.setName("b");
        beerVM.setId(beerId);
        HttpEntity<BeerVM> httpEntity = new HttpEntity(beerVM);
        //when
        ResponseEntity putEntity = this.restTemplate.exchange(resource() + "/" + beerId, HttpMethod.PUT, httpEntity, Object.class);
        //then
        assertEquals(HttpStatus.CREATED, putEntity.getStatusCode());
    }

    @Test @Order(3)
    public void shouldPatchOnPatch() {
        //given
        BeerVM beerVM = new BeerVM();
        beerVM.setName("c");
        //when
        this.restTemplate.patchForObject(resource() + "/" + beerId, beerVM, Object.class);
        //then
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerId, BeerVM.class);
        assertEquals(beerVM.getName(), findEntity.getBody().getName());
    }

    @Test @Order(4)
    public void shouldDelete() {
        //when
        this.restTemplate.delete(resource() + "/" + beerId);
        //then
        ResponseEntity<BeerVM> findEntity = this.restTemplate.getForEntity(resource() + "/" + beerId, BeerVM.class);
        assertEquals(HttpStatus.BAD_REQUEST, findEntity.getStatusCode());
    }

    @Test @Order(5)
    public void shouldReturnBadRequestWhenDeletedResourceDontExists() {
        ResponseEntity deleteEntity = this.restTemplate.exchange(resource() + "/" + beerId, HttpMethod.DELETE, null, Object.class);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, deleteEntity.getStatusCode());
    }

    private String resource() {
        return "http://localhost:" + port + "/beerhouse/beers";
    }


}
