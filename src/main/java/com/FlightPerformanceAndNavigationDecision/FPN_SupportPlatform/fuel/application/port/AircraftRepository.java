package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.port;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import java.util.Optional;

public interface AircraftRepository {
    Optional<Aircraft> findByType(String type);
}
