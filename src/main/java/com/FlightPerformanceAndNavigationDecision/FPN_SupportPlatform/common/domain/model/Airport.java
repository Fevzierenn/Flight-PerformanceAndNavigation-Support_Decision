package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Airport {

    private final String icaoCode;      //LTFS
    private final double runwayLength;  //metre
}
