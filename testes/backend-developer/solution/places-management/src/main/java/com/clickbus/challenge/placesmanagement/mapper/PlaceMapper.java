package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.Place;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PlaceMapper {
    PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class );

    PlaceResponse placeToPlaceResponse(Place place);
}