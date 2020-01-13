package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import com.clickbus.challenge.placesmanagement.exception.Error;
import com.clickbus.challenge.placesmanagement.exception.ResponseEntityExceptionHandler;
import com.clickbus.challenge.placesmanagement.service.CityService;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @InjectMocks
    private CityController cityController;

    @Mock
    private CityService cityService;

    private CityRequest cityRequest;
    private CityResponse cityResponse;

    @Before
    public void setUp() {
        cityRequest = CityRequest.builder()
                .name("São Paulo")
                .stateId(new Long(1))
                .build();

        cityResponse = CityResponse.builder()
                .id(new Long(1))
                .name("São Paulo")
                .state(StateResponse.builder()
                        .id(new Long(1))
                        .name("São Paulo")
                        .abbreviation("SP")
                        .build())
                .build();

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(cityController)
                .setControllerAdvice(new ResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void shouldCreateCityReturnHttpBadRequest() throws Exception {
        CityRequest badCityRequest = CityRequest.builder()
                                        .name("S")
                                        .build();

        mockMvc.perform(post(CityController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(badCityRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateCityReturnHttpNotFound() throws Exception {
        when(cityService.create(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post(CityController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(cityRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateCityWithJsonRequest() throws Exception {
        when(cityService.create(any())).thenReturn(cityResponse);

        mockMvc.perform(post(CityController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(cityRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.state.id", equalTo(1)))
                .andExpect(jsonPath("$.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.state.abbreviation", equalTo("SP")));
    }

    @Test
    public void shouldCreateCityWithXmlRequest() throws Exception {
        StringWriter writerXml = new StringWriter();
        JAXB.marshal(cityRequest, writerXml);

        when(cityService.create(any())).thenReturn(cityResponse);

        mockMvc.perform(post(CityController.BASE_URL)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .content(writerXml.toString()))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldReturnNotFoundCityWithInvalidId() throws Exception {
        when(cityService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CityController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$",
                        equalTo(Error.builder()
                                    .message("Resource Not Found")
                                    .build().toString()
                        )
                ));
    }

    @Test
    public void shouldReturnCityWithValidId() throws Exception {
        when(cityService.findById(anyLong())).thenReturn(cityResponse);

        mockMvc.perform(get(CityController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.state.id", equalTo(1)))
                .andExpect(jsonPath("$.state.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.state.abbreviation", equalTo("SP")));
    }


}