package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.rule;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception.BusinessException;

/**
 * Domain business rule:
 * Checks whether the runway length is sufficient for takeoff.
 */


public class RunWayLengthRule {
    
    public void validateRunWayLength(double requiredRunwayLength, Airport airport){
        if(airport.getRunwayLength() < requiredRunwayLength){
            throw new BusinessException
            ("Runway length insufficient for required takeoff distance.");
        }
    }
}
