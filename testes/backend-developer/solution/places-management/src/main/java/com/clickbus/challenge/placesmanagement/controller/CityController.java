package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.CityRequest;
import com.clickbus.challenge.placesmanagement.dto.response.CityResponse;
import com.clickbus.challenge.placesmanagement.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(CityController.BASE_URL)
public class CityController {
    public static final String BASE_URL = "/cities";

    private final CityService cityService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse createCity(@Valid @RequestBody CityRequest cityRequest){
        return cityService.create(cityRequest);
    }

    @GetMapping(path="/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    @ResponseStatus(HttpStatus.OK)
    public CityResponse getCityById(@PathVariable("id") Long id) {
        return cityService.findById(id);
    }

}
