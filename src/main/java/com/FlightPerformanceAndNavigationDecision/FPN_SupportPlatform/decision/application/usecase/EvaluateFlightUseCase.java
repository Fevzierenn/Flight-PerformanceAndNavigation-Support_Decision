package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.application.usecase;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception.BusinessException;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.DecisionResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.FlightStatus;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.service.DecisionEngine;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.usecase.CalculateFuelRequirementUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model.FuelResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase.CalculateRouteDistanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.NavigationResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AircraftRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AirportRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.usecase.CalculateTakeoffPerformanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.model.TakeOffPerformanceResult;

import java.util.ArrayList;
import java.util.List;

public class EvaluateFlightUseCase {

    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase;
    private final CalculateRouteDistanceUseCase calculateRouteDistanceUseCase;
    private final CalculateFuelRequirementUseCase calculateFuelRequirementUseCase;
    private final DecisionEngine decisionEngine;

    public EvaluateFlightUseCase(
            AircraftRepository aircraftRepository,
            AirportRepository airportRepository,
            CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase,
            CalculateRouteDistanceUseCase calculateRouteDistanceUseCase,
            CalculateFuelRequirementUseCase calculateFuelRequirementUseCase,
            DecisionEngine decisionEngine
    ) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.calculateTakeoffPerformanceUseCase = calculateTakeoffPerformanceUseCase;
        this.calculateRouteDistanceUseCase = calculateRouteDistanceUseCase;
        this.calculateFuelRequirementUseCase = calculateFuelRequirementUseCase;
        this.decisionEngine = decisionEngine;
    }

    public DecisionResult execute(
            String aircraftType,
            String departureIcaoCode,
            String arrivalIcaoCode,
            String alternativeIcaoCode,
            double takeoffWeight
    ) {
        List<String> reasons = new ArrayList<>();
        FlightStatus status = FlightStatus.SAFE;

        Aircraft aircraft = aircraftRepository.findByType(aircraftType)
                .orElseThrow(() -> new BusinessException("Aircraft not found: " + aircraftType));
        Airport departureAirport = airportRepository.findByIcaoCode(departureIcaoCode)
                .orElseThrow(() -> new BusinessException("Departure airport not found: " + departureIcaoCode));
        Airport destinationAirport = airportRepository.findByIcaoCode(arrivalIcaoCode)
                .orElseThrow(() -> new BusinessException("Destination airport not found: " + arrivalIcaoCode));
        Airport alternativeAirport = airportRepository.findByIcaoCode(alternativeIcaoCode)
                .orElseThrow(() -> new BusinessException("Alternative airport not found: " + alternativeIcaoCode));

        TakeOffPerformanceResult takeoffResult = null;
        NavigationResult toDestinationRoute = null;
        NavigationResult toAlternativeRoute = null;
        FuelResult fuelResult = null;

        try {
            takeoffResult = calculateTakeoffPerformanceUseCase.execute(aircraftType, departureIcaoCode, takeoffWeight);
        } catch (BusinessException e) {
            status = FlightStatus.NOT_ALLOWED;
            reasons.add("Performance violation: " + e.getMessage());
        }

        try {
            toDestinationRoute = calculateRouteDistanceUseCase.execute(departureIcaoCode, arrivalIcaoCode);
        } catch (BusinessException e) {
            status = FlightStatus.NOT_ALLOWED;
            reasons.add("Navigation to destination violation: " + e.getMessage());
        }

        try {
            toAlternativeRoute = calculateRouteDistanceUseCase.execute(arrivalIcaoCode, alternativeIcaoCode);
        } catch (BusinessException e) {
            status = FlightStatus.NOT_ALLOWED;
            reasons.add("Navigation to alternative violation: " + e.getMessage());
        }

        if (toDestinationRoute != null && toAlternativeRoute != null) {
            double totalDistance = toDestinationRoute.getDistanceInKm() + toAlternativeRoute.getDistanceInKm();
            try {
                fuelResult = calculateFuelRequirementUseCase.execute(aircraftType, totalDistance);
            } catch (BusinessException e) {
                status = FlightStatus.NOT_ALLOWED;
                reasons.add("Fuel violation: " + e.getMessage());
            }
        }

        double baseLandingDistance = aircraft.getBaseTakeOffDistance() * 0.8; // Landing is estimated at 80% of base takeoff distance
        DecisionResult finalDecision = decisionEngine.evaluate(
                takeoffResult,
                departureAirport.getRunwayLength(),
                destinationAirport.getRunwayLength(),
                alternativeAirport.getRunwayLength(),
                baseLandingDistance,
                fuelResult,
                aircraft.getMaxFuelCapacity()
        );

        if (status == FlightStatus.NOT_ALLOWED || finalDecision.getStatus() == FlightStatus.NOT_ALLOWED) {
            status = FlightStatus.NOT_ALLOWED;
        } else if (finalDecision.getStatus() == FlightStatus.RISKY) {
            status = FlightStatus.RISKY;
        }

        reasons.addAll(finalDecision.getReasons());

        if (reasons.size() > 1 && reasons.contains("All flight performance, navigation, diversion, and fuel requirements are safely met.")) {
            reasons.remove("All flight performance, navigation, diversion, and fuel requirements are safely met.");
        }

        return new DecisionResult(status, reasons);
    }
}
