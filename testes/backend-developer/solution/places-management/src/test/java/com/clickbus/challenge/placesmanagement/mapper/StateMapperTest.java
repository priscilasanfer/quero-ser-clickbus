package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.StateRequest;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StateMapperTest {
    @Test
    public void shouldMapStateToStateResponse() {
        State state = State.builder()
                .id(new Long(1))
                .name("São Paulo")
                .abbreviation("SP")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        StateResponse stateResponse = StateMapper.INSTANCE.stateToStateResponse(state);

        assertThat(stateResponse.getId(), is(state.getId()));
        assertThat(stateResponse.getName(), is(state.getName()));
        assertThat(stateResponse.getAbbreviation(), is(state.getAbbreviation()));
    }

    @Test
    public void shouldMapStateRequestToState() {
        StateRequest stateRequest = StateRequest.builder()
                .name("São Paulo")
                .abbreviation("SP")
                .build();

        State state = StateMapper.INSTANCE.stateRequestToState(stateRequest);

        assertThat(state.getName(), is(stateRequest.getName()));
        assertThat(state.getAbbreviation(), is(stateRequest.getAbbreviation()));

    }

}