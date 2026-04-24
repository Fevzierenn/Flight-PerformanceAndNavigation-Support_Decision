package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @GetMapping("")
    public String getMethodName() {
        return new String("Performance Controller works fine");
    }
    
    
}
