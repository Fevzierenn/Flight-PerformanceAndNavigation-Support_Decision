package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.api.dtos;

import lombok.Getter;

@Getter
public class CalculateRouteDistanceResponse {
    private final double distanceInKm;

    public CalculateRouteDistanceResponse(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

}
