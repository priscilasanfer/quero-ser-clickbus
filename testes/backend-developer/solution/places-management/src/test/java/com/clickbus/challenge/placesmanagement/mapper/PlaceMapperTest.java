package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.domain.Place;
import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlaceMapperTest {
    @Test
    public void shouldMapPlaceToPlaceResponse() {
        Place place = Place.builder()
                .id(new Long(1))
                .name("Parque Ibirapuera")
                .slug("parque-ibirapuera")
                .city(City.builder()
                        .id(new Long(1))
                        .name("São Paulo")
                        .state(State.builder()
                                .id(new Long(1))
                                .name("São Paulo")
                                .abbreviation("SP")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .build())
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        PlaceResponse placeResponse = PlaceMapper.INSTANCE.placeToPlaceResponse(place);

        assertThat(placeResponse.getId(), is(place.getId()));
        assertThat(placeResponse.getName(), is(place.getName()));
        assertThat(placeResponse.getSlug(), is(place.getSlug()));
        assertThat(placeResponse.getCity().getId(), is(place.getCity().getId()));
    }

}