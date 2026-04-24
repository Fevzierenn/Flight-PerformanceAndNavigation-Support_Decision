package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @GetMapping("")
    public String performanceControllerHealth() {
        return new String("Performance Controller works fine");
    }
    
    
}
