package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.api.controller;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.api.dtos.CalculateFuelRequest;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.api.dtos.CalculateFuelResponse;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.usecase.CalculateFuelRequirementUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model.FuelResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    private final CalculateFuelRequirementUseCase calculateFuelRequirementUseCase;

    public FuelController(CalculateFuelRequirementUseCase calculateFuelRequirementUseCase) {
        this.calculateFuelRequirementUseCase = calculateFuelRequirementUseCase;
    }

    @GetMapping("")
    public String fuelControllerHealth() {
        return "fuel Controller works fine";
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculateFuelResponse> calculateFuel(@Valid @RequestBody CalculateFuelRequest request) {
        FuelResult result = calculateFuelRequirementUseCase.execute(request.getAircraftType(), request.getDistanceInKm());
        return ResponseEntity.ok(new CalculateFuelResponse(
                result.getRequiredFuel(),
                result.getBurnFuel(),
                result.getReserveFuel(),
                result.isSufficient()
        ));
    }
}
