package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class );

    CityResponse cityToCityResponse(City city);

    @Mapping(source = "stateId", target = "state.id")
    City cityRequestToCity(CityRequest cityRequest);
}