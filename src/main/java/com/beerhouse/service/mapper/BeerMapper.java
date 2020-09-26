package com.beerhouse.service.mapper;

import com.beerhouse.domain.Beer;
import com.beerhouse.web.rest.vm.BeerVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    BeerVM map(Beer beer);

    Beer map(BeerVM beerVM);

}
