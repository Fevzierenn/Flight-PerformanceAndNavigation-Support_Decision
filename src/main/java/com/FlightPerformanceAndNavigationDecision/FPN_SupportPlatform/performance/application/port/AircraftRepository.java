package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port;

import java.util.Optional;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;

public interface AircraftRepository {
    Optional<Aircraft> findByType(String type);

    
}
