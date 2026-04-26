package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port;

import java.util.Optional;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;

public interface AirportRepository {
    Optional<Airport> findByIcaoCode(String icaoCode);

}
