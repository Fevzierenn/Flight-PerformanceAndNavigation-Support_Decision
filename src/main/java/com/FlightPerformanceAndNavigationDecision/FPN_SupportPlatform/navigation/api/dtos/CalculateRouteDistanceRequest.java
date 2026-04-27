package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class CalculateRouteDistanceRequest {
    
    @NotBlank(message = "Departure airport ICAO code is required")
    private String departureAirportIcaoCode;
    @NotBlank(message = "Arrival airport ICAO code is required")
    private String arrivalAirportIcaoCode;

}
