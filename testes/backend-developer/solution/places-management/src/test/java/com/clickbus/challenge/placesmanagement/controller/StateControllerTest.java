package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.StateRequest;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import com.clickbus.challenge.placesmanagement.exception.ResponseEntityExceptionHandler;
import com.clickbus.challenge.placesmanagement.service.StateService;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StateControllerTest {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateService;

    private StateRequest stateRequest;
    private StateResponse stateResponse;

    @Before
    public void setUp() {
        stateRequest = StateRequest.builder()
                .name("São Paulo")
                .abbreviation("SP")
                .build();

        stateResponse = StateResponse.builder()
                .id(1L)
                .name("São Paulo")
                .abbreviation("SP")
                .build();

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(stateController)
                .setControllerAdvice(new ResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void shouldCreateStateReturnHttpBadRequest() throws Exception {
        StateRequest badStateRequest = StateRequest.builder()
                                        .name("S")
                                        .build();

        mockMvc.perform(post(StateController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(badStateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateStateWithJsonRequest() throws Exception {
        when(stateService.create(any())).thenReturn(stateResponse);

        mockMvc.perform(post(StateController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(stateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.abbreviation", equalTo("SP")));
    }

    @Test
    public void shouldReturnNotFoundStateWithInvalidId() throws Exception {
        when(stateService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(StateController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo("Resource Not Found")));
    }

    @Test
    public void shouldReturnStateWithValidId() throws Exception {
        when(stateService.findById(anyLong())).thenReturn(stateResponse);

        mockMvc.perform(get(StateController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.abbreviation", equalTo("SP")));
    }


}