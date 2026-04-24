package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fuel")
public class FuelController {
    
    @GetMapping("")
    public String fuelControllerHealth() {
        return new String("fuel Controller works fine");
    }
}
