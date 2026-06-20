# Flight Performance and Navigation Support Decision Platform

AeroDecision is a domain-driven, decoupled software platform designed to assist flight dispatchers and operations controllers in evaluating flight plan feasibility. By assessing takeoff performance margins, route distances, diversion alternatives, and fuel limitations, the platform determines whether a planned flight is Safe, Risky, or Not Allowed.

## Architecture and Design Patterns

The platform is designed following Clean Architecture, Domain-Driven Design (DDD), and Hexagonal (Ports and Adapters) principles.

### Key Architectural Concepts

*   **Bounded Contexts:** The domain is partitioned into isolated subdomains: Decision, Fuel, Navigation, and Performance. Each context contains its own API layer, application use cases, domain entities/services, and infrastructure persistence adapters.
*   **Hexagonal Architecture:**
    *   **Driving Adapters (API):** REST Controllers handle HTTP requests and DTO mappings.
    *   **Application Boundary (Ports):** Use cases orchestrate domain actions and communicate with external resources via repository ports (interfaces).
    *   **Domain Core:** Framework-independent models, services, rules, and value objects containing the actual aviation calculations and business checks.
    *   **Driven Adapters (Infrastructure):** In-memory repositories implementing the repository ports to query mocked data without database dependencies.
*   **Shared Kernel (Common):** Objects shared across multiple contexts, including coordinate values, weight structures, and business exception handlers.

---

## Core Subdomains

### 1. Decision Bounded Context
Orchestrates and consolidates calculations from all other contexts.
*   **EvaluateFlightUseCase:** Orchestrates the flow by fetching airport and aircraft models, running sub-context use cases, and calling the Decision Engine.
*   **DecisionEngine:** Applies final safety thresholds. For example, it flags a flight as Risky if the required runway length exceeds 85 percent of the available runway, or if the fuel consumption exceeds 80 percent of capacity. It marks the flight as Not Allowed if absolute structural or safety limits are exceeded.

### 2. Fuel Bounded Context
Computes fuel sufficiency based on aircraft consumption rates and flight distance.
*   **FuelCalculator:** Validates if the aircraft has the structural capacity to carry the required fuel (covering departure to arrival, diversion to alternative, and safety reserves).

### 3. Navigation Bounded Context
Determines route distances using geospatial coordinates.
*   **HaversineCalculator:** Implements the Haversine formula to compute great-circle distances between departure, destination, and alternative airport coordinates.
*   **RouteDistanceCalculator:** Computes segment distances for both the main route and diversion route.

### 4. Performance Bounded Context
Validates aerodynamic and structural constraints at takeoff.
*   **TakeoffPerformanceCalculator:** Calculates the required takeoff runway length based on aircraft type and current takeoff weight.
*   **RunWayLengthRule:** Validates whether the departure airport's runway is structurally long enough for the computed takeoff distance.

---

## Technology Stack

### Backend
*   Java 17
*   Spring Boot 3
*   Apache Maven
*   Lombok
*   Jakarta Validation API

---

## Core API Endpoints

### 1. Evaluate Flight Safety
*   **Endpoint:** `POST /api/flights/evaluate`
*   **Request Body:**
    ```json
    {
      "aircraftType": "A320",
      "departureAirportIcaoCode": "LTFM",
      "arrivalAirportIcaoCode": "LTDA",
      "alternativeAirportIcaoCode": "LTAI",
      "takeoffWeight": 70000.0
    }
    ```
*   **Response Body:**
    ```json
    {
      "status": "SAFE",
      "reasons": [
        "All flight performance, navigation, diversion, and fuel requirements are safely met."
      ]
    }
    ```

### 2. Calculate Route Distance
*   **Endpoint:** `POST /api/navigation/route-distance`
*   **Request Body:**
    ```json
    {
      "departureAirportIcaoCode": "LTFM",
      "arrivalAirportIcaoCode": "LTDA"
    }
    ```

### 3. Calculate Takeoff Performance
*   **Endpoint:** `POST /api/performance/takeoff`
*   **Request Body:**
    ```json
    {
      "aircraftType": "A320",
      "airportIcaoCode": "LTFM",
      "takeoffWeight": 70000.0
    }
    ```

### 4. Calculate Fuel Requirements
*   **Endpoint:** `POST /api/fuel/calculate`
*   **Request Parameters / Body:**
    ```json
    {
      "aircraftType": "A320",
      "distanceInKm": 1000.0
    }
    ```

---

## Getting Started

### Prerequisites
*   Java Development Kit (JDK) 17 or higher
*   Maven 3.6+ or the included Maven wrapper (`mvnw`)

### Running the Backend Service
1. Navigate to the project root directory.
2. Compile and run the Spring Boot application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The server will start on port `8080`. You can access the API endpoints at `http://localhost:8080`.


---

## Static In-Memory Data

The following data sets are configured in the in-memory persistence layer for evaluation:

### Airports
*   **LTFM (Istanbul Airport):** Runway Length: 3000m, Coordinates: 41.275, 28.751
*   **LTDA (Hatay Airport):** Runway Length: 3000m, Coordinates: 36.362, 36.282
*   **LTAI (Antalya Airport):** Runway Length: 3400m, Coordinates: 36.900, 30.800

### Aircraft Specifications
*   **Airbus A320:** Maximum Takeoff Weight (MTOW): 78,000kg, Fuel Capacity: 19,000kg, Burn Rate: 2.5kg/km, Base Takeoff Distance: 1500m
*   **Boeing 737:** Maximum Takeoff Weight (MTOW): 79,000kg, Fuel Capacity: 20,000kg, Burn Rate: 2.6kg/km, Base Takeoff Distance: 1600m
*   **Airbus A330:** Maximum Takeoff Weight (MTOW): 242,000kg, Fuel Capacity: 97,000kg, Burn Rate: 6.0kg/km, Base Takeoff Distance: 2200m

