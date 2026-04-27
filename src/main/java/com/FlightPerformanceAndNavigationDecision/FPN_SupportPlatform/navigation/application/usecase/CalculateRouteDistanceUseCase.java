package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase;
import java.util.Optional;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception.BusinessException;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.port.AirportRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.NavigationResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.Route;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.service.RouteDistanceCalculator;

public class CalculateRouteDistanceUseCase {    
    

    private final RouteDistanceCalculator routeDistanceCalculator;
    private final AirportRepository airportRepository;

    public CalculateRouteDistanceUseCase(RouteDistanceCalculator routeDistanceCalculator, AirportRepository airportRepository) {
        this.routeDistanceCalculator = routeDistanceCalculator;
        this.airportRepository = airportRepository;
    }

    public NavigationResult execute(String departureAirportIcaoCode, String arrivalAirportIcaoCode){
        if(departureAirportIcaoCode.equals(arrivalAirportIcaoCode)){
            throw new BusinessException("Departure and arrival airports cannot be the same!");
        }
        
        Airport departureAirport = airportRepository.findByIcaoCode(departureAirportIcaoCode).orElseThrow(
           ()-> new BusinessException("Departure airport not found "+ departureAirportIcaoCode)
        );
        Airport arrivalAirport = airportRepository.findByIcaoCode(arrivalAirportIcaoCode).orElseThrow(
           ()-> new BusinessException("Arrival airport not found "+ arrivalAirportIcaoCode)
        );

        Route route = new Route(
            departureAirport,
            arrivalAirport
        );
        return routeDistanceCalculator.calculateDistance(route);

    }
}