package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.service;

import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.domain.valueObject.Coordinate;

public class HaversineCalculator {
    private static final double EARTH_RADIUS = 6371; // Radius in kilometers
    
    protected double calculateHaversineDistance(Coordinate from , Coordinate to)
    {
        // distance between latitudes and longitudes
        double latDistance = Math.toRadians(to.getLatitude() - from.getLatitude());
        double lonDistance = Math.toRadians(to.getLongitude() - from.getLongitude());

        // convert to radians
        double lat1 = Math.toRadians(from.getLatitude());
        double lat2 = Math.toRadians(to.getLatitude());

        // apply formulae
        double a = Math.pow(Math.sin(latDistance / 2), 2) + 
                   Math.pow(Math.sin(lonDistance / 2), 2) * 
                   Math.cos(lat1) * 
                   Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }

}