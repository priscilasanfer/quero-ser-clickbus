package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.PlaceRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(PlaceController.BASE_URL)
public class PlaceController {
    public static final String BASE_URL = "/places";

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PlaceResponse> createPlace(@Valid @RequestBody PlaceRequest placeRequest){
        return new ResponseEntity<>(PlaceResponse.builder()
                .id(new Long(1))
                .name(placeRequest.getName())
                .city(CityResponse.builder()
                        .id(new Long(1))
                        .name("São Paulo")
                        .state(StateResponse.builder()
                                .id(new Long(1))
                                .name("São Paulo")
                                .abbreviation("SP")
                                .build())
                        .build())
                .build(),
                HttpStatus.CREATED);
    }
   /* public PlaceDTO createPlace(@Valid @RequestBody CreateUpdatePlaceDTO placeDTO) {
    }

    public PlaceDTO updatePlace(@PathVariable Long id, @Valid @RequestBody CreateUpdatePlaceDTO placeDTO) {
    }*/


    @GetMapping(path="/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<PlaceResponse> getPlaceById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                PlaceResponse.builder()
                        .id(new Long(1))
                        .name("Ibirapuera")
                        .slug("ibirapuera")
                        .city(CityResponse.builder()
                                .id(new Long(1))
                                .name("Sao Paulo")
                                .state(StateResponse.builder()
                                        .id(new Long(1))
                                        .name("São Paulo")
                                        .abbreviation("SP")
                                        .build())
                                .build())
                        .build(),
                HttpStatus.OK);
    }

    /*public List<PlaceDTO> getPlaces(@PathVariable("name") Optional<String> name) {
    }*/
}
