package com.clickbus.challenge.placesmanagement.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Error {
    private String message;
}
