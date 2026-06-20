package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class EvaluateFlightRequest {

    @NotBlank(message = "Aircraft type is required")
    private String aircraftType;

    @NotBlank(message = "Departure airport ICAO code is required")
    private String departureAirportIcaoCode;

    @NotBlank(message = "Arrival airport ICAO code is required")
    private String arrivalAirportIcaoCode;

    @NotBlank(message = "Alternative airport ICAO code is required")
    private String alternativeAirportIcaoCode;

    @Positive(message = "Takeoff weight must be positive")
    private double takeoffWeight;
}
