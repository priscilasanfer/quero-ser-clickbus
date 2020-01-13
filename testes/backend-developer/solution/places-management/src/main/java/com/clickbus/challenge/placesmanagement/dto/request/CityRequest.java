package com.clickbus.challenge.placesmanagement.dto.request;

import io.swagger.annotations.ApiModelProperty;
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
public class CityRequest {

    @ApiModelProperty(value = "name", example = "São Paulo")
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must not be less than 2 characters")
    private String name;

    @ApiModelProperty(value = "stateId", example = "1")
    @NotNull(message = "StateId cannot be null")
    private Long stateId;

}
