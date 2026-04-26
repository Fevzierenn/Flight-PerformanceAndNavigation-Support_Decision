package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.usecase;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.valueObject.Weight;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception.BusinessException;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AircraftRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AirportRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.model.TakeOffPerformanceResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.service.TakeoffPerformanceCalculator;

public class CalculateTakeoffPerformanceUseCase {
        
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final TakeoffPerformanceCalculator calculator;

    public CalculateTakeoffPerformanceUseCase(
            AircraftRepository aircraftRepository,
            AirportRepository airportRepository,
            TakeoffPerformanceCalculator calculator
    ) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.calculator = calculator;
    }

     public TakeOffPerformanceResult execute(Aircraft aircraft, Airport airport, Weight takeOffWeight){
                Aircraft aircraftModel = aircraftRepository
                .findByType(aircraft.getType())
                .orElseThrow(() ->
                        new BusinessException("Aircraft not found: " + aircraft.getType())
                );        
                
                Airport airportModel = airportRepository
                .findByIcaoCode(airport.getIcaoCode())
                .orElseThrow(() ->
                        new BusinessException("Airport not found: " + airport.getIcaoCode())
                );

        return calculator.calculate(aircraftModel, airportModel, takeOffWeight);

                

     }




}
