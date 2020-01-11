package com.clickbus.challenge.placesmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StateResponse {
    private Long id;
    private String name;
    private String abbreviation;
}
