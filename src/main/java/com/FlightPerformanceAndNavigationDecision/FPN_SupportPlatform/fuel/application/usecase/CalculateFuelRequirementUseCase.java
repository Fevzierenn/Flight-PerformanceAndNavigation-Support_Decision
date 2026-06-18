package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.usecase;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception.BusinessException;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.port.AircraftRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model.FuelResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.service.FuelCalculator;

public class CalculateFuelRequirementUseCase {

    private final AircraftRepository aircraftRepository;
    private final FuelCalculator fuelCalculator;

    public CalculateFuelRequirementUseCase(AircraftRepository aircraftRepository, FuelCalculator fuelCalculator) {
        this.aircraftRepository = aircraftRepository;
        this.fuelCalculator = fuelCalculator;
    }

    public FuelResult execute(String aircraftType, double distanceInKm) {
        Aircraft aircraft = aircraftRepository.findByType(aircraftType)
                .orElseThrow(() -> new BusinessException("Aircraft not found: " + aircraftType));

        return fuelCalculator.calculateRequiredFuel(aircraft, distanceInKm);
    }
}
