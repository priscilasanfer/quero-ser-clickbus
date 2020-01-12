package com.clickbus.challenge.placesmanagement.mapper;

import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.StateRequest;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface StateMapper {
    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class );

    StateResponse stateToStateResponse(State state);
    State stateRequestToState(StateRequest stateRequest);
}