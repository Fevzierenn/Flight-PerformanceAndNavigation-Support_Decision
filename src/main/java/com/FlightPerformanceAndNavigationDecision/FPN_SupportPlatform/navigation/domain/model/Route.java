package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Route {
    private final Airport departureAirport;
    private final Airport arrivalAirport;

}
