package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.config;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AircraftRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AirportRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.usecase.CalculateTakeoffPerformanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.rule.RunWayLengthRule;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.service.TakeoffPerformanceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PerformanceBeanConfig {


    @Bean
    public CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase( AircraftRepository aircraftRepository,
                                                                                  AirportRepository airportRepository,
                                                                                  TakeoffPerformanceCalculator calculator){
        return new CalculateTakeoffPerformanceUseCase(aircraftRepository, airportRepository, calculator);

    }

    @Bean
    public RunWayLengthRule runWayLengthRule(){
        return new RunWayLengthRule();
    }

    @Bean
    public TakeoffPerformanceCalculator takeoffPerformanceCalculator(RunWayLengthRule runWayLengthRule){
        return new TakeoffPerformanceCalculator(runWayLengthRule);
    }



}
