package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.config;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.port.AircraftRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.usecase.CalculateFuelRequirementUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.service.FuelCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FuelBeanConfig {

    @Bean
    public FuelCalculator fuelCalculator() {
        return new FuelCalculator();
    }

    @Bean
    public CalculateFuelRequirementUseCase calculateFuelRequirementUseCase(
            AircraftRepository aircraftRepository,
            FuelCalculator fuelCalculator
    ) {
        return new CalculateFuelRequirementUseCase(aircraftRepository, fuelCalculator);
    }
}
