package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/navigation")
public class NavigationController {
    

    @GetMapping("")
    public String getMethodName() {
        return new String("Navigation Controller works fine");
    }
}
