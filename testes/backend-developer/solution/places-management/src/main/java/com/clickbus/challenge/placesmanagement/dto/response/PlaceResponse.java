package com.clickbus.challenge.placesmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PlaceResponse {
    private Long id;
    private String name;
    private String slug;
    private CityResponse city;

}
