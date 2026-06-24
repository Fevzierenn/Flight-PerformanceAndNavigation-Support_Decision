package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FuelResult {
    private final double requiredFuel;   // kg
    private final double burnFuel;       // kg
    private final double reserveFuel;    // kg
    private final boolean isSufficient;  // true if requiredFuel <= maxFuelCapacity
}
