package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CityMapperTest {
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

        CityResponse cityResponse = CityMapper.INSTANCE.cityToCityResponse(city);

        assertThat(cityResponse.getId(), is(city.getId()));
        assertThat(cityResponse.getName(), is(city.getName()));
        assertThat(cityResponse.getState().getId(), is(city.getState().getId()));
    }

}