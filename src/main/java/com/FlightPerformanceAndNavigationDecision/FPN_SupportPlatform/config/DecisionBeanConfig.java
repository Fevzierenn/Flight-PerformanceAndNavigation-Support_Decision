package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.config;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.application.usecase.EvaluateFlightUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.service.DecisionEngine;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.usecase.CalculateFuelRequirementUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase.CalculateRouteDistanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AircraftRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AirportRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.usecase.CalculateTakeoffPerformanceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecisionBeanConfig {

    @Bean
    public DecisionEngine decisionEngine() {
        return new DecisionEngine();
    }

    @Bean
    public EvaluateFlightUseCase evaluateFlightUseCase(
            AircraftRepository aircraftRepository,
            AirportRepository airportRepository,
            CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase,
            CalculateRouteDistanceUseCase calculateRouteDistanceUseCase,
            CalculateFuelRequirementUseCase calculateFuelRequirementUseCase,
            DecisionEngine decisionEngine
    ) {
        return new EvaluateFlightUseCase(
                aircraftRepository,
                airportRepository,
                calculateTakeoffPerformanceUseCase,
                calculateRouteDistanceUseCase,
                calculateFuelRequirementUseCase,
                decisionEngine
        );
    }
}
