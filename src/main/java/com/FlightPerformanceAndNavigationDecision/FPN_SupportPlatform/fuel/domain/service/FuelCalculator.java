package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.service;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model.FuelResult;

public class FuelCalculator {

    public FuelResult calculateRequiredFuel(Aircraft aircraft, double distanceInKm) {
        if (distanceInKm <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        
        double burnFuel = distanceInKm * aircraft.getFuelBurnRatePerKm();
        double reserveFuel = aircraft.getReserveFuel();
        double requiredFuel = burnFuel + reserveFuel;
        boolean isSufficient = requiredFuel <= aircraft.getMaxFuelCapacity();

        return new FuelResult(requiredFuel, burnFuel, reserveFuel, isSufficient);
    }
}
