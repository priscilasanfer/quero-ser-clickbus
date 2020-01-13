package com.clickbus.challenge.placesmanagement.service;

import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.StateRequest;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import com.clickbus.challenge.placesmanagement.mapper.StateMapper;
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
public class StateServiceTest {
    private StateService stateService;

    @Mock
    private StateRepository stateRepository;

    private State state;
    private StateRequest stateRequest;
    private StateResponse stateResponse;

    @Before
    public void setUp() {
        stateRequest = StateRequest.builder()
            .name("São Paulo")
            .abbreviation("SP")
            .build();

        state = State.builder()
            .id(new Long(1))
            .name("São Paulo")
            .abbreviation("SP")
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        stateResponse = StateMapper.INSTANCE.stateToStateResponse(state);

        MockitoAnnotations.initMocks(this);

        stateService = new StateService(stateRepository);
    }

    @Test
    public void shouldCreateState() {
        when(stateRepository.save(any())).thenReturn(state);

        StateResponse actualStateResponse = stateService.create(stateRequest);

        assertThat(actualStateResponse, is(stateResponse));
    }

    @Test
    public void shouldReturnStateWithValidId() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(state));

        StateResponse actualStateResponse = stateService.findById(new Long(1));

        assertThat(actualStateResponse, is(stateResponse));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowsResourceNotFoundExceptionWithInvalidId() {
        when(stateRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        stateService.findById(new Long(1));
    }


}


