package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.infrastructure.persistence;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.valueObject.Coordinate;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.port.AirportRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryNavigationAirportRepository implements AirportRepository {

    private static final Map<String, Airport> AIRPORTS = Map.of(

            "LTFM", new Airport("ISTANBUL AIRPORT","LTFM", 3000, new Coordinate(41.2622, 28.7278)),
            "LTDA", new Airport("HATAY AIRPORT","LTDA", 3000, new Coordinate(36.362778, 36.282223)),
            "LTAI", new Airport("ANTALYA AIPORT","LTAI", 3400, new Coordinate(36.8987, 30.8006))
            );

    @Override
    public Optional<Airport> findByIcaoCode(String icaoCode) {
        return Optional.ofNullable(AIRPORTS.get(icaoCode));
    }
}
