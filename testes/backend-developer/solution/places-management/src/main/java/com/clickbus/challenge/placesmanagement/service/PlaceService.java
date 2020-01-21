package com.clickbus.challenge.placesmanagement.service;

import com.clickbus.challenge.placesmanagement.domain.Place;
import com.clickbus.challenge.placesmanagement.dto.request.PlaceRequest;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import com.clickbus.challenge.placesmanagement.mapper.PlaceMapper;
import com.clickbus.challenge.placesmanagement.repository.CityRepository;
import com.clickbus.challenge.placesmanagement.repository.PlaceRepository;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;

    private final Slugify slugify = new Slugify();

    public PlaceResponse create(PlaceRequest placeRequest) {
        Place place = Place.builder()
                .name(placeRequest.getName())
                .slug(slugify.slugify(placeRequest.getName()))
                .city(cityRepository
                        .findById(placeRequest.getCityId())
                        .orElseThrow(ResourceNotFoundException::new))
                .build();

        place = placeRepository.save(place);

        return PlaceMapper.INSTANCE.placeToPlaceResponse(place);
    }

    public PlaceResponse findById(Long id) {
        Place place = placeRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return PlaceMapper.INSTANCE.placeToPlaceResponse(place);
    }

    public List<PlaceResponse> findAll() {
        return placeRepository.findAll().stream()
                .map(place -> PlaceMapper.INSTANCE.placeToPlaceResponse(place))
                .collect(Collectors.toList());
    }

    public List<PlaceResponse> findByName(String name) {
        return placeRepository.findByNameContainingIgnoreCase(name).stream()
                .map(place -> PlaceMapper.INSTANCE.placeToPlaceResponse(place))
                .collect(Collectors.toList());
    }

    public PlaceResponse update(Long id, PlaceRequest placeRequest) {
        Place place = placeRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        place.setName(placeRequest.getName());
        place.setSlug(slugify.slugify(place.getName()));
        place.setCity(cityRepository
                .findById(placeRequest.getCityId())
                .orElseThrow(ResourceNotFoundException::new));

        place = placeRepository.save(place);

        return PlaceMapper.INSTANCE.placeToPlaceResponse(place);
    }

}