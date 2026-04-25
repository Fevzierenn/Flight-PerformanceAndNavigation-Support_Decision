package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.service;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Aircraft;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.model.Airport;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.valueObject.Weight;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception.BusinessException;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.rule.RunWayLengthRule;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.model.TakeOffPerformanceResult;


/**
 * Domain service responsible for takeoff performance calculations.
 */


public class TakeoffPerformanceCalculator {
    private final RunWayLengthRule runwayLengthRule;

    public TakeoffPerformanceCalculator(RunWayLengthRule runwayLengthRule) {
        this.runwayLengthRule = runwayLengthRule;
    }

 
    public TakeOffPerformanceResult calculate(
            Aircraft aircraft,
            Airport airport,
            Weight takeOffWeight
    ) {

        validateMTOW(aircraft, takeOffWeight);

        double requiredRunwayLength =
                calculateRequiredRunwayLength(aircraft, takeOffWeight);

        runwayLengthRule.validateRunWayLength(requiredRunwayLength, airport);

        return new TakeOffPerformanceResult(requiredRunwayLength);
    }


    private void validateMTOW(Aircraft aircraft, Weight takeOffWeight){
        if(takeOffWeight.isGreaterThan(aircraft.getMaxTakeOffWeight())){
            throw new BusinessException("Maximum takeoff weight exceeded.");
        }
    }

    private double calculateRequiredRunwayLength(Aircraft aircraft,Weight takeOffWeight){
        return aircraft.getBaseTakeOffDistance() *( takeOffWeight.ratioTo(aircraft.getMaxTakeOffWeight()));
    }
          
    
}

