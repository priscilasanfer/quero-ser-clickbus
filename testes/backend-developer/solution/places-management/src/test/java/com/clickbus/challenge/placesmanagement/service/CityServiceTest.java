package com.clickbus.challenge.placesmanagement.service;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import com.clickbus.challenge.placesmanagement.mapper.CityMapper;
import com.clickbus.challenge.placesmanagement.repository.CityRepository;
import com.clickbus.challenge.placesmanagement.repository.StateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateRepository stateRepository;

    private State state;
    private City city;
    private CityRequest cityRequest;
    private CityResponse cityResponse;

    @Before
    public void setUp() {
        state = State.builder()
                .id(1L)
                .name("São Paulo")
                .abbreviation("SP")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        cityRequest = CityRequest.builder()
            .name("São Paulo")
            .stateId(1L)
            .build();

        city = City.builder()
            .id(1L)
            .name("São Paulo")
            .state(state)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        cityResponse = CityMapper.INSTANCE.cityToCityResponse(city);

        MockitoAnnotations.initMocks(this);

        cityService = new CityService(cityRepository, stateRepository);
    }

    @Test
    public void shouldCreateCityWithValidStateId() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(state));
        when(cityRepository.save(any())).thenReturn(city);

        CityResponse actualCityResponse = cityService.create(cityRequest);

        assertThat(actualCityResponse, is(cityResponse));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowsResourceNotFoundExceptionWithInvalidStateId()  {
        when(stateRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        cityService.create(cityRequest);
    }

    @Test
    public void shouldReturnCityWithValidId() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        CityResponse actualCityResponse = cityService.findById(1L);

        assertThat(actualCityResponse, is(cityResponse));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowsResourceNotFoundExceptionWithInvalidId() {
        when(cityRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        cityService.findById(1L);
    }


}


