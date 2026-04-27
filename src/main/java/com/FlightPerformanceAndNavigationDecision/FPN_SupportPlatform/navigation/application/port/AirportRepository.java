package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.port;

import java.util.Optional;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;

public interface AirportRepository {
    Optional<Airport> findByIcaoCode(String icaoCode);

}
