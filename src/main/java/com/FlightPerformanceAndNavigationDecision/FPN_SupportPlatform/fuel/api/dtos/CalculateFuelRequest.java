package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CalculateFuelRequest {

    @NotBlank(message = "Aircraft type is required")
    private String aircraftType;

    @Positive(message = "Distance must be positive")
    private double distanceInKm;
}
