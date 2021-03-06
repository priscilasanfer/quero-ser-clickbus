package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.PlaceRequest;
import com.clickbus.challenge.placesmanagement.dto.response.PlaceResponse;
import com.clickbus.challenge.placesmanagement.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(PlaceController.BASE_URL)
public class PlaceController {
    public static final String BASE_URL = "/places";

    private final PlaceService placeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceResponse createPlace(@Valid @RequestBody PlaceRequest placeRequest){
        return placeService.create(placeRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaceResponse updatePlace(@PathVariable Long id, @Valid @RequestBody PlaceRequest placeRequest){
        return placeService.update(id, placeRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaceResponse getPlaceById(@PathVariable("id") Long id) {
        return placeService.findById(id);
    }

    @GetMapping("/list/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceResponse> getPlaceByName(@PathVariable("name") String name) {
        return placeService.findByName(name);
    }

    @GetMapping({"", "/list"})
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceResponse> getPlaces() {
        return placeService.findAll();
    }
}
