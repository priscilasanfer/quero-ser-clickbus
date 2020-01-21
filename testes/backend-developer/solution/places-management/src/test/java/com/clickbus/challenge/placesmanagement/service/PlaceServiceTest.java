package com.clickbus.challenge.placesmanagement.service;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.domain.Place;
import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.PlaceRequest;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import com.clickbus.challenge.placesmanagement.mapper.PlaceMapper;
import com.clickbus.challenge.placesmanagement.repository.CityRepository;
import com.clickbus.challenge.placesmanagement.repository.PlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {
    private PlaceService placeService;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private CityRepository cityRepository;

    private City city;
    private Place place;
    private PlaceRequest placeRequest;
    private PlaceResponse placeResponse;

    @Before
    public void setUp() {
        city = City.builder()
            .id(1L)
            .name("São Paulo")
            .state(State.builder()
                    .id(1L)
                    .name("São Paulo")
                    .abbreviation("SP")
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build())
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        placeRequest = PlaceRequest.builder()
                .name("Parque Ibirapuera")
                .cityId(1L)
                .build();

        place = Place.builder()
                .id(1L)
                .name("Parque Ibirapuera")
                .slug("parque-ibirapuera")
                .city(city)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        placeResponse = PlaceMapper.INSTANCE.placeToPlaceResponse(place);

        MockitoAnnotations.initMocks(this);

        placeService = new PlaceService(placeRepository,cityRepository);
    }

    @Test
    public void shouldCreatePlaceWithValidCityId() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        when(placeRepository.save(any())).thenReturn(place);

        PlaceResponse actualPlaceResponse = placeService.create(placeRequest);

        assertThat(actualPlaceResponse, is(placeResponse));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowsResourceNotFoundExceptionWithInvalidCityId()  {
        when(cityRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        placeService.create(placeRequest);
    }

    @Test
    public void shouldReturnPlaceWithValidId() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));

        PlaceResponse actualPlaceResponse = placeService.findById(1L);

        assertThat(actualPlaceResponse, is(placeResponse));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldCreateThrowsResourceNotFoundExceptionWithInvalidId() {
        when(placeRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        placeService.findById(1L);
    }

    @Test
    public void shouldReturnPlaceWithValidName() {
        List<Place> places = Arrays.asList(
                place,

                Place.builder()
                        .id(2L)
                        .name("Shopping Ibirapuera")
                        .slug("shopping-ibirapuera")
                        .city(city)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build());

        List<PlaceResponse> placesResponse = places.stream()
                        .map(place -> PlaceMapper.INSTANCE.placeToPlaceResponse(place))
                        .collect(Collectors.toList());

        when(placeRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(places);

        List<PlaceResponse> actualPlacesResponse = placeService.findByName("ibirapuera");

        assertThat(actualPlacesResponse, is(placesResponse));
    }

    @Test
    public void shouldReturnAllPlaces() {
        List<Place> places = Arrays.asList(
                place,
                Place.builder()
                        .id(2L)
                        .name("Shopping Ibirapuera")
                        .slug("shopping-ibirapuera")
                        .city(city)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build());

        List<PlaceResponse> placesResponse = places.stream()
                .map(place -> PlaceMapper.INSTANCE.placeToPlaceResponse(place))
                .collect(Collectors.toList());

        when(placeRepository.findAll()).thenReturn(places);

        List<PlaceResponse> actualPlacesResponse = placeService.findAll();

        assertThat(actualPlacesResponse, is(placesResponse));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldUpdateThrowsResourceNotFoundExceptionWithInvalidId()  {
        when(placeRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        placeService.update(1L, placeRequest);
    }

    @Test
    public void shouldUpdatePlaceWithValidCityId() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));
        when(placeRepository.save(any())).thenReturn(place);

        PlaceResponse actualPlaceResponse = placeService.update(1L, placeRequest);

        assertThat(actualPlaceResponse, is(placeResponse));
    }


}


