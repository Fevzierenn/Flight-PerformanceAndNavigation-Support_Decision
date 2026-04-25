package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Aircraft {

    private final String type;                  // A320
    private final double maxTakeOffWeight;      //kg
    private final double baseTakeOffDistance;   //metre
    
    
}
