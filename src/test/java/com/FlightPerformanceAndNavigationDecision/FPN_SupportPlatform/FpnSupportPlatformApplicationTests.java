package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.application.usecase.CalculateTakeoffPerformanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.performance.domain.model.TakeOffPerformanceResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.application.usecase.CalculateRouteDistanceUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.navigation.domain.model.NavigationResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.application.usecase.CalculateFuelRequirementUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.fuel.domain.model.FuelResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.application.usecase.EvaluateFlightUseCase;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.DecisionResult;
import com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.decision.domain.model.FlightStatus;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FpnSupportPlatformApplicationTests {

	@Autowired
	private CalculateTakeoffPerformanceUseCase calculateTakeoffPerformanceUseCase;

	@Autowired
	private CalculateRouteDistanceUseCase calculateRouteDistanceUseCase;

	@Autowired
	private CalculateFuelRequirementUseCase calculateFuelRequirementUseCase;

	@Autowired
	private EvaluateFlightUseCase evaluateFlightUseCase;

	@Test
	void contextLoads() {
	}

	@Test
	void testCalculateTakeoffPerformance_Success() {
		TakeOffPerformanceResult result = calculateTakeoffPerformanceUseCase.execute("A320", "LTFM", 70000.0);
		assertNotNull(result);
		assertTrue(result.getRequiredRunwayLength() > 0.0);
	}

	@Test
	void testCalculateRouteDistance_Success() {
		NavigationResult result = calculateRouteDistanceUseCase.execute("LTFM", "LTAI");
		assertNotNull(result);
		assertTrue(result.getDistanceInKm() > 0.0);
	}

	@Test
	void testCalculateFuelRequirement_Success() {
		// A320 with 500km distance: burn = 1250kg, reserve = 1500kg, total = 2750kg <= 19000kg capacity
		FuelResult result = calculateFuelRequirementUseCase.execute("A320", 500.0);
		assertNotNull(result);
		assertTrue(result.getRequiredFuel() > 0.0);
		assertTrue(result.isSufficient());
	}

	@Test
	void testCalculateFuelRequirement_Insufficient() {
		// A320 with very large distance exceeding capacity
		FuelResult result = calculateFuelRequirementUseCase.execute("A320", 10000.0);
		assertNotNull(result);
		assertFalse(result.isSufficient());
	}

	@Test
	void testEvaluateFlight_Safe() {
		// A320 departing LTFM to LTDA with LTAI as alternative
		DecisionResult result = evaluateFlightUseCase.execute("A320", "LTFM", "LTDA", "LTAI", 70000.0);
		assertNotNull(result);
		assertEquals(FlightStatus.SAFE, result.getStatus());
		assertTrue(result.getReasons().size() > 0);
	}

	@Test
	void testEvaluateFlight_Risky_DueToRunway() {
		// B737 departing from LTFM (3000m runway) with weight 78000kg requires ~2557m takeoff distance (85.2% of runway)
		DecisionResult result = evaluateFlightUseCase.execute("B737", "LTFM", "LTDA", "LTAI", 78000.0);
		assertNotNull(result);
		assertEquals(FlightStatus.RISKY, result.getStatus());
		assertTrue(result.getReasons().stream().anyMatch(r -> r.contains("Tight departure runway margin")));
	}

	@Test
	void testEvaluateFlight_NotAllowed_DueToMTOW() {
		DecisionResult result = evaluateFlightUseCase.execute("A320", "LTFM", "LTDA", "LTAI", 85000.0); // > 78000kg MTOW
		assertNotNull(result);
		assertEquals(FlightStatus.NOT_ALLOWED, result.getStatus());
		assertTrue(result.getReasons().stream().anyMatch(r -> r.contains("Maximum takeoff weight exceeded")));
	}
}
