package com.clickbus.challenge.placesmanagement.service;

import com.clickbus.challenge.placesmanagement.domain.State;
import com.clickbus.challenge.placesmanagement.dto.request.StateRequest;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import com.clickbus.challenge.placesmanagement.mapper.StateMapper;
import com.clickbus.challenge.placesmanagement.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    public StateResponse create(StateRequest stateRequest) {
        State state = StateMapper.INSTANCE.stateRequestToState(stateRequest);

        state = stateRepository.save(state);

        return StateMapper.INSTANCE.stateToStateResponse(state);
    }

    public StateResponse findById(Long id) {
        State state = stateRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return StateMapper.INSTANCE.stateToStateResponse(state);
    }

}