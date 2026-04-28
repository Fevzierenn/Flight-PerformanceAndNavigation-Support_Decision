package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.config;


import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.port.AirportRepository;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase.CalculateRouteDistanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.service.HaversineCalculator;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.service.RouteDistanceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NavigationBeanConfig {

    @Bean
    public HaversineCalculator haversineCalculator() {
        return new HaversineCalculator();
    }

    @Bean
    public RouteDistanceCalculator routeDistanceCalculator(
            HaversineCalculator haversineCalculator
    ) {
        return new RouteDistanceCalculator(haversineCalculator);
    }

    @Bean
    public CalculateRouteDistanceUseCase calculateRouteDistanceUseCase(
            RouteDistanceCalculator routeDistanceCalculator,
            AirportRepository airportRepository
    ) {
        return new CalculateRouteDistanceUseCase(
                routeDistanceCalculator,
                airportRepository
        );
    }
}
