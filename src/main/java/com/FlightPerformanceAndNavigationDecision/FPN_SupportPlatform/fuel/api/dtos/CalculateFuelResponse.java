package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.api.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CalculateFuelResponse {
    private final double requiredFuel;
    private final double burnFuel;
    private final double reserveFuel;
    private final boolean isSufficient;
}
