package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;


@Getter
public class TakeoffPerformanceRequest {
    
    @NotBlank
    private String aircraftType;

    @NotBlank
    private String airportIcaoCode;

    @Positive
    private double takeoffWeight;

}
