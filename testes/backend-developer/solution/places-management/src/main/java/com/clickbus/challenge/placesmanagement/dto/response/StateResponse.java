package com.clickbus.challenge.placesmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateResponse {
    private Long id;
    private String name;
    private String abbreviation;
}
