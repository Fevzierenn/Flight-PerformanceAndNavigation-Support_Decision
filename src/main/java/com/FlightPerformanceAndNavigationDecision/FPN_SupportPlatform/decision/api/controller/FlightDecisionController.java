package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.api.controller;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.api.dtos.EvaluateFlightRequest;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.api.dtos.EvaluateFlightResponse;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.application.usecase.EvaluateFlightUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.DecisionResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
public class FlightDecisionController {

    private final EvaluateFlightUseCase evaluateFlightUseCase;

    public FlightDecisionController(EvaluateFlightUseCase evaluateFlightUseCase) {
        this.evaluateFlightUseCase = evaluateFlightUseCase;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<EvaluateFlightResponse> evaluateFlight(@Valid @RequestBody EvaluateFlightRequest request) {
        DecisionResult result = evaluateFlightUseCase.execute(
                request.getAircraftType(),
                request.getDepartureAirportIcaoCode(),
                request.getArrivalAirportIcaoCode(),
                request.getAlternativeAirportIcaoCode(),
                request.getTakeoffWeight()
        );
        return ResponseEntity.ok(new EvaluateFlightResponse(
                result.getStatus().name(),
                result.getReasons()
        ));
    }
}
