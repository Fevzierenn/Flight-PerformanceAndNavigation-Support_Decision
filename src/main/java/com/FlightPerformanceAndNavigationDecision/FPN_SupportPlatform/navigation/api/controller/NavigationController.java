package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.api.dtos.CalculateRouteDistanceRequest;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.api.dtos.CalculateRouteDistanceResponse;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase.CalculateRouteDistanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.NavigationResult;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/navigation")
public class NavigationController {
    
    private final CalculateRouteDistanceUseCase calculateRouteDistanceUseCase;

    public NavigationController(CalculateRouteDistanceUseCase calculateRouteDistanceUseCase) {
        this.calculateRouteDistanceUseCase = calculateRouteDistanceUseCase;
    }


    @GetMapping("")
    public String navigationControllerHealth() {
        return new String("Navigation Controller works fine");
    }

    @PostMapping("/route-distance")
    public ResponseEntity<CalculateRouteDistanceResponse> calculateRouteDistance(@Valid @RequestBody CalculateRouteDistanceRequest request) {
        NavigationResult result = calculateRouteDistanceUseCase.execute(request.getDepartureAirportIcaoCode(), request.getArrivalAirportIcaoCode());
        return ResponseEntity.ok(new CalculateRouteDistanceResponse(result.getDistanceInKm())) ;
    }
    

    
}
