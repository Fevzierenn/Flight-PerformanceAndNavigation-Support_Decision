package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.service;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.NavigationResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.Route;
public class RouteDistanceCalculator {

    private final HaversineCalculator haversineCalculator;

    public RouteDistanceCalculator(HaversineCalculator haversineCalculator){
        this.haversineCalculator = haversineCalculator;
    }
    public NavigationResult calculateDistance(Route route) {
        Airport departureAirport = route.getDepartureAirport();
        Airport arrivalAirport = route.getArrivalAirport();

        //haversine (from and to) departure vs arrival airport
        double distance = haversineCalculator.calculateHaversineDistance(departureAirport.getCoordinate(), 
        arrivalAirport.getCoordinate());

        return new NavigationResult(distance);
        }

   
}