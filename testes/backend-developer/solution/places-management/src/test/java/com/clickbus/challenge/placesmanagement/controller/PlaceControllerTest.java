package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.PlaceRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import com.clickbus.challenge.placesmanagement.exception.ResponseEntityExceptionHandler;
import com.clickbus.challenge.placesmanagement.service.PlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.bind.JAXB;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlaceControllerTest {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @InjectMocks
    private PlaceController placeController;

    @Mock
    private PlaceService placeService;

    private PlaceRequest placeRequest;
    private PlaceResponse placeResponse;
    private List<PlaceResponse> placesResponse;

    @Before
    public void setUp() {
        CityResponse cityResponse = CityResponse.builder()
                .id(new Long(1))
                .name("São Paulo")
                .state(StateResponse.builder()
                        .id(new Long(1))
                        .name("São Paulo")
                        .abbreviation("SP")
                        .build())
                .build();

        placeRequest = PlaceRequest.builder()
                .name("Parque Ibirapuera")
                .cityId(new Long(1))
                .build();

        placeResponse = PlaceResponse.builder()
                .id(new Long(1))
                .name("Parque Ibirapuera")
                .slug("parque-ibirapuera")
                .city(cityResponse)
                .build();

        placesResponse = Arrays.asList(
                placeResponse,
                PlaceResponse.builder()
                        .id(new Long(2))
                        .name("Shopping Ibirapuera")
                        .slug("shopping-ibirapuera")
                        .city(cityResponse)
                        .build(),
                PlaceResponse.builder()
                        .id(new Long(3))
                        .name("Avenida Paulista")
                        .slug("avenida-paulista")
                        .city(cityResponse)
                        .build()
                );

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(placeController)
                .setControllerAdvice(new ResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void shouldCreatePlaceReturnHttpBadRequest() throws Exception {
        PlaceRequest badPlaceRequest = PlaceRequest.builder()
                                        .name("P")
                                        .build();

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(badPlaceRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreatePlaceReturnHttpNotFound() throws Exception {
        when(placeService.create(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(placeRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreatePlaceWithJsonRequest() throws Exception {
        when(placeService.create(any())).thenReturn(placeResponse);

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(placeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Parque Ibirapuera")))
                .andExpect(jsonPath("$.slug", equalTo("parque-ibirapuera")))
                .andExpect(jsonPath("$.city.id", equalTo(1)))
                .andExpect(jsonPath("$.city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.id", equalTo(1)))
                .andExpect(jsonPath("$.city.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.abbreviation", equalTo("SP")));
    }

    @Test
    public void shouldReturnNotFoundPlaceWithInvalidId() throws Exception {
        when(placeService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo("Resource Not Found")));
    }

    @Test
    public void shouldReturnPlaceWithValidId() throws Exception {
        when(placeService.findById(anyLong())).thenReturn(placeResponse);

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Parque Ibirapuera")))
                .andExpect(jsonPath("$.slug", equalTo("parque-ibirapuera")))
                .andExpect(jsonPath("$.city.id", equalTo(1)))
                .andExpect(jsonPath("$.city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.id", equalTo(1)))
                .andExpect(jsonPath("$.city.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.abbreviation", equalTo("SP")));
    }

    @Test
    public void shouldUpdatePlaceReturnHttpBadRequest() throws Exception {
        PlaceRequest badPlaceRequest = PlaceRequest.builder()
                .name("P")
                .build();

        mockMvc.perform(put(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(badPlaceRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdatePlaceReturnHttpNotFound() throws Exception {
        when(placeService.update(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(placeRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdatePlaceWithJsonRequest() throws Exception {
        when(placeService.update(anyLong(), any())).thenReturn(placeResponse);

        mockMvc.perform(put(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(placeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Parque Ibirapuera")))
                .andExpect(jsonPath("$.slug", equalTo("parque-ibirapuera")))
                .andExpect(jsonPath("$.city.id", equalTo(1)))
                .andExpect(jsonPath("$.city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.id", equalTo(1)))
                .andExpect(jsonPath("$.city.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.abbreviation", equalTo("SP")));
    }

    @Test
    public void shouldReturnAllPlacesUsingBaseURLPath() throws Exception {
        when(placeService.findAll()).thenReturn(placesResponse);

        mockMvc.perform(get(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("Parque Ibirapuera")))
                .andExpect(jsonPath("$.[0].slug", equalTo("parque-ibirapuera")))
                .andExpect(jsonPath("$.[0].city.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.[0].city.state.id", equalTo(1)))
                .andEx
    pect(jsonPath("$.[0].city.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.[0].city.state.abbreviation", equalTo("SP")))
                .andExpect(jsonPath("$.[1].name", equalTo("Shopping Ibirapuera")))
                .andExpect(jsonPath("$.[2].name", equalTo("Avenida Paulista")));
    }

    @Test
    public void shouldReturnAllPlacesUsingBaseURLPathPlusList() throws Exception {
        when(placeService.findAll()).thenReturn(placesResponse);

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/list"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("Parque Ibirapuera")))
                .andExpect(jsonPath("$.[0].slug", equalTo("parque-ibirapuera")))
                .andExpect(jsonPath("$.[0].city.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.[0].city.state.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.[0].city.state.abbreviation", equalTo("SP")))
                .andExpect(jsonPath("$.[1].name", equalTo("Shopping Ibirapuera")))
                .andExpect(jsonPath("$.[2].name", equalTo("Avenida Paulista")));
    }

    @Test
    public void shouldReturnPlacesByName() throws Exception {
        when(placeService.findByName(anyString()))
                .thenReturn(placesResponse.stream()
                            .filter(place -> place.getName().toLowerCase().contains("ibirapuera"))
                            .collect(Collectors.toList()));

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/list/").concat("ibirapuera"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("Parque Ibirapuera")))
                .andExpect(jsonPath("$.[0].slug", equalTo("parque-ibirapuera")))
                .andExpect(jsonPath("$.[0].city.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.[0].city.state.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.[0].city.state.abbreviation", equalTo("SP")))
                .andExpect(jsonPath("$.[1].name", equalTo("Shopping Ibirapuera")));
    }

}