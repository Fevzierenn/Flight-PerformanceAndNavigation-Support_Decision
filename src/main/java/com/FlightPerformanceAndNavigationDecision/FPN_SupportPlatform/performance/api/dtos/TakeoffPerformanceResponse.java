package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.dtos;

public class TakeoffPerformanceResponse {
    
    private final double requiredRunwayLength;

    public TakeoffPerformanceResponse(double requiredRunwayLength) {
        this.requiredRunwayLength = requiredRunwayLength;
    }

    public double getRequiredRunwayLength() {
        return requiredRunwayLength;
    }

}
