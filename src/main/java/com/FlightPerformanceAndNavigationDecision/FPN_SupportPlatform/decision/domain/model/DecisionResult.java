package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DecisionResult {
    private final FlightStatus status;
    private final List<String> reasons;
}
