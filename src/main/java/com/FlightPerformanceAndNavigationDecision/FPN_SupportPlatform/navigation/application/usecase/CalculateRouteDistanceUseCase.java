package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.NavigationResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.Route;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.service.RouteDistanceCalculator;

public class CalculateRouteDistanceUseCase {    
    

    private final RouteDistanceCalculator routeDistanceCalculator;

    public CalculateRouteDistanceUseCase(RouteDistanceCalculator routeDistanceCalculator) {
        this.routeDistanceCalculator = routeDistanceCalculator;
    }

    public NavigationResult execute(Route route){
        return routeDistanceCalculator.calculateDistance(route);
    
    }
}