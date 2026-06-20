package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.api.dtos;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EvaluateFlightResponse {
    private final String status;
    private final List<String> reasons;
}
