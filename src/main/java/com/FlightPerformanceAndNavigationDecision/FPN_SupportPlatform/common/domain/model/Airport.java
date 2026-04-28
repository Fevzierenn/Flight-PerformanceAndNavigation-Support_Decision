package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.valueObject.Coordinate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Airport {

    private final String airportName;
    private final String icaoCode;      //LTFS
    private final double runwayLength;  //metre
    private final Coordinate coordinate;
    
}
