package com.clickbus.challenge.placesmanagement.service;

import com.clickbus.challenge.placesmanagement.domain.City;
import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import com.clickbus.challenge.placesmanagement.mapper.CityMapper;
import com.clickbus.challenge.placesmanagement.repository.CityRepository;
import com.clickbus.challenge.placesmanagement.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public CityResponse create(CityRequest cityRequest) {
        City city = City.builder()
                .name(cityRequest.getName())
                .state(stateRepository
                        .findById(cityRequest.getStateId())
                        .orElseThrow(ResourceNotFoundException::new))
                .build();

        city = cityRepository.save(city);

        return CityMapper.INSTANCE.cityToCityResponse(city);
    }

    public CityResponse findById(Long id) {
        City city = cityRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return CityMapper.INSTANCE.cityToCityResponse(city);
    }

}