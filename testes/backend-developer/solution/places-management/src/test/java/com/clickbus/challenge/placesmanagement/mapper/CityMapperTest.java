package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CityMapperTest {
    @Test
    public void shouldMapCityToCityResponse() {
        City city = City.builder()
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
                .build();

        CityResponse cityResponse = CityMapper.INSTANCE.cityToCityResponse( city );

        assertThat(cityResponse.getId(), is(city.getId()));
        assertThat(cityResponse.getName(), is(city.getName()));
        assertThat(cityResponse.getState().getId(), is(city.getState().getId()));
    }

    @Test
    public void shouldMapCityRequestToCity() {
        CityRequest cityRequest = CityRequest.builder()
                .name("São Paulo")
                .stateId(new Long(1))
                .build();

        City city = CityMapper.INSTANCE.cityRequestToCity(cityRequest);

        assertThat(city.getName(), is(cityRequest.getName()));
        assertThat(city.getState().getId(), is(cityRequest.getStateId()));

    }

}