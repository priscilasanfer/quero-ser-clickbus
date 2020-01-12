package com.clickbus.challenge.placesmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequest {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must not be less than 2 characters")
    private String name;

    @NotNull(message = "Slug cannot be null")
    @Size(min = 2, message = "Slug must not be less than 2 characters")
    private String slug;

    @NotNull(message = "CityId cannot be null")
    private Long cityId;

}
