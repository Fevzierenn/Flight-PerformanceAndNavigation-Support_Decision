package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.infrastructure.persistence;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.port.AircraftRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryPerformanceAircraftRepository implements AircraftRepository {

    private static final Map<String, Aircraft> AIRCRAFTS = Map.of(
            "A320", new Aircraft("A320", 78000.0, 1500.0, 19000.0, 2.5, 1500.0),
            "B737", new Aircraft("B737", 79000.0, 1600.0, 20000.0, 2.6, 1600.0),
            "A330", new Aircraft("A330", 242000.0, 2200.0, 97000.0, 6.0, 4500.0)
    );

    @Override
    public Optional<Aircraft> findByType(String type) {
        return Optional.ofNullable(AIRCRAFTS.get(type));
    }
}
