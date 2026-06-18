package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.service;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.DecisionResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.FlightStatus;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.model.TakeOffPerformanceResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model.FuelResult;

import java.util.ArrayList;
import java.util.List;

public class DecisionEngine {

    public DecisionResult evaluate(
            TakeOffPerformanceResult takeoffResult,
            double departureRunwayLength,
            double destinationRunwayLength,
            double alternativeRunwayLength,
            double baseLandingDistance,
            FuelResult fuelResult,
            double maxFuelCapacity
    ) {
        List<String> reasons = new ArrayList<>();
        FlightStatus status = FlightStatus.SAFE;

        // 1. Departure Takeoff Performance Check
        if (takeoffResult != null) {
            double requiredRunway = takeoffResult.getRequiredRunwayLength();
            if (requiredRunway > departureRunwayLength) {
                status = FlightStatus.NOT_ALLOWED;
                reasons.add("Required takeoff runway length (" + String.format("%.1f", requiredRunway) + "m) exceeds actual departure runway length (" + departureRunwayLength + "m).");
            } else if (requiredRunway / departureRunwayLength > 0.85) {
                status = FlightStatus.RISKY;
                reasons.add("Tight departure runway margin: required takeoff distance (" + String.format("%.1f", requiredRunway) + "m) is over 85% of departure runway length (" + departureRunwayLength + "m).");
            }
        }

        // 2. Destination Landing Runway Check
        if (baseLandingDistance > destinationRunwayLength) {
            status = FlightStatus.NOT_ALLOWED;
            reasons.add("Required landing distance (" + String.format("%.1f", baseLandingDistance) + "m) exceeds destination runway length (" + destinationRunwayLength + "m).");
        } else if (baseLandingDistance / destinationRunwayLength > 0.85) {
            if (status != FlightStatus.NOT_ALLOWED) {
                status = FlightStatus.RISKY;
            }
            reasons.add("Tight destination runway margin: landing distance (" + String.format("%.1f", baseLandingDistance) + "m) is over 85% of destination runway length (" + destinationRunwayLength + "m).");
        }

        // 3. Alternative Landing Runway Check
        if (baseLandingDistance > alternativeRunwayLength) {
            status = FlightStatus.NOT_ALLOWED;
            reasons.add("Required landing distance (" + String.format("%.1f", baseLandingDistance) + "m) exceeds alternative runway length (" + alternativeRunwayLength + "m).");
        } else if (baseLandingDistance / alternativeRunwayLength > 0.85) {
            if (status != FlightStatus.NOT_ALLOWED) {
                status = FlightStatus.RISKY;
            }
            reasons.add("Tight alternative runway margin: landing distance (" + String.format("%.1f", baseLandingDistance) + "m) is over 85% of alternative runway length (" + alternativeRunwayLength + "m).");
        }

        // 4. Fuel Sufficiency Check (covering: Departure -> Destination -> Alternative + Reserves)
        if (fuelResult != null) {
            if (!fuelResult.isSufficient()) {
                status = FlightStatus.NOT_ALLOWED;
                reasons.add("Required flight fuel (" + String.format("%.1f", fuelResult.getRequiredFuel()) + "kg) exceeds aircraft fuel capacity (" + maxFuelCapacity + "kg).");
            } else if (fuelResult.getRequiredFuel() / maxFuelCapacity > 0.80) {
                if (status != FlightStatus.NOT_ALLOWED) {
                    status = FlightStatus.RISKY;
                }
                reasons.add("High fuel consumption: flight requires " + String.format("%.1f", fuelResult.getRequiredFuel()) + "kg of fuel (including diversion), which uses " + String.format("%.1f", (fuelResult.getRequiredFuel() / maxFuelCapacity) * 100) + "% of total fuel capacity.");
            }
        }

        if (reasons.isEmpty()) {
            reasons.add("All flight performance, navigation, diversion, and fuel requirements are safely met.");
        }

        return new DecisionResult(status, reasons);
    }
}
