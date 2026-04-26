package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.dtos.TakeoffPerformanceRequest;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.dtos.TakeoffPerformanceResponse;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.usecase.CalculateTakeoffPerformanceUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    private final CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase;

    public PerformanceController(CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase){
        this.calculateTakeoffPerformanceUseCase = calculateTakeoffPerformanceUseCase;
    }


    @GetMapping("")
    public String performanceControllerHealth() {
        return new String("Performance Controller works fine");
    }
    
    @PostMapping("/takeoff")
    public ResponseEntity<TakeoffPerformanceResponse> calculateTakeOffPerformance(
        @Valid @RequestBody TakeoffPerformanceRequest request) {
        var result = calculateTakeoffPerformanceUseCase.execute(
          request.getAircraftType(),
          request.getAirportIcaoCode(),
          request.getTakeoffWeight()
        );
        
        return ResponseEntity.ok(new TakeoffPerformanceResponse(result.getRequiredRunwayLength()));
    }
    
    
    
    
    
}
