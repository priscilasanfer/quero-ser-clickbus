package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
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
@RequestMapping(CityController.BASE_URL)
public class CityController {
    public static final String BASE_URL = "/cities";

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest){
        return new ResponseEntity<>(CityResponse.builder()
                        .id(new Long(1))
                        .name("São Paulo")
                        .state(StateResponse.builder()
                                .id(new Long(1))
                                .name("São Paulo")
                                .abbreviation("SP")
                                .build())
                        .build(),
                HttpStatus.CREATED);
    }

    @GetMapping(path="/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<CityResponse> getCityById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                CityResponse.builder()
                    .id(new Long(1))
                    .name("Sao Paulo")
                    .state(StateResponse.builder()
                            .id(new Long(1))
                            .name("São Paulo")
                            .abbreviation("SP")
                            .build())
                    .build(),
                HttpStatus.OK);
    }

}
