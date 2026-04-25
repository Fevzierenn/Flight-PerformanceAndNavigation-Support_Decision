package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.valueObject;

import javax.management.RuntimeErrorException;

import lombok.Getter;

@Getter
public class Weight {

    private final double value;

    public Weight(double value) {
        if(value > 0){
                this.value = value;
        }
        else{
            throw new RuntimeException("Weight cannot be negative or zero");
        }
    }

    public boolean isGreaterThan(double otherWeight) {
        return this.value > otherWeight;
    }

    public double ratioTo(double maxValue) {
            return this.value / maxValue;
        }



    
}
