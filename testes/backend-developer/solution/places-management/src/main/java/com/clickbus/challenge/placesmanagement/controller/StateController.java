package com.clickbus.challenge.placesmanagement.controller;

import com.clickbus.challenge.placesmanagement.dto.request.StateRequest;
import com.clickbus.challenge.placesmanagement.dto.response.StateResponse;
import com.clickbus.challenge.placesmanagement.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping(StateController.BASE_URL)
public class StateController {
    public static final String BASE_URL = "/states";

    private final StateService stateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateResponse createState(@Valid @RequestBody StateRequest stateRequest){
        return stateService.create(stateRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StateResponse getStateById(@PathVariable("id") Long id) {
        return stateService.findById(id);
    }

}
