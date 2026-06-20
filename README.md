# Flight-PerformanceAndNavigation-Support_Decision

Project Structure and Relationship Diagram
This document presents a comprehensive, precise, and structural overview of the AeroDecision (Flight Performance & Navigation Support Decision) platform. It details the system architecture, file layout, bounded context separation, sequence workflows, and class relationships using standard visual modeling.

```markdown
# AeroDecision (Flight Performance & Navigation Support Decision Platform)

AeroDecision is a clean, modular flight decision support platform designed to evaluate flight safety parameters. The system processes performance, navigation, fuel, and general risk analysis data to determine whether a flight is safe to operate.

---

## 1. General Decision Logic & Flow

The system takes a flight input and evaluates it through the following logical workflow to determine the final safety status:


```

[Flight Input]
│
├──> Performance ───> "Can it take off given the runway conditions?"
├──> Navigation  ───> "Is the route and alternative airport suitable?"
├──> Fuel        ───> "Is there enough fuel requirement?"
└──> Risk        ───> "General decision and constraints evaluation"
│
▼
[Flight Status]: SAFE / RISKY / NOT_ALLOWED

```

<img width="1137" height="142" alt="AeroDecision Flow" src="https://github.com/user-attachments/assets/3d944565-8fb5-43c3-8da7-9705f5ad16c2" />

---

## 2. Directory Layout & Bounded Contexts

The codebase is structured into **Bounded Contexts** under the primary package prefix `com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform`. Each bounded context represents a distinct functional domain:

<img width="1432" height="258" alt="Directory Layout" src="https://github.com/user-attachments/assets/0f3e5e44-f141-44ea-92eb-ff5bbb46605d" />

### Modules and Responsibilities
* **`decision`**: The core orchestration layer that collects data from other contexts to make the final flight allowance decision.
* **`performance`**: Evaluates takeoff performance based on airport constraints, runway length, and weather conditions.
* **`navigation`**: Validates route distances and the suitability of alternate airports.
* **`fuel`**: Calculates fuel requirements based on flight distance and reserve policies.
* **`common`**: A **Shared Kernel** module containing shared data structures (`FlightId`, `Airport`, `Distance`) used across the project.
* **`config`**: The centralized configuration layer handling Spring Bean definitions.

---

## 3. Hexagonal (Ports & Adapters) Layering in Bounded Contexts

Each bounded context (except `common` and `config`) strictly adheres to **Clean/Hexagonal Architecture** principles. The diagram below illustrates how the core domain logic is decoupled from external infrastructure (driving/driven adapters) using the `performance` context as an example:

<img width="1469" height="449" alt="Hexagonal Architecture" src="https://github.com/user-attachments/assets/87cbbfe7-a73f-43b2-8d12-992b6adb2172" />

### Layer Breakdown & Responsibilities

<img width="1094" height="379" alt="Layer Descriptions" src="https://github.com/user-attachments/assets/f590b4b6-e094-41b0-b03c-d72876728ba0" />

| Layer | Responsibility | Key Components / Examples |
| :--- | :--- | :--- |
| **Domain (Core)** | Pure business logic and rules, completely free of external framework dependencies. | `Flight`, `PerformanceRules`, `FuelPolicy` |
| **Application (Ports)** | Defines Use Cases and Port interfaces that manage interaction with the outside world. | `EvaluateFlightUseCase`, `CalculateFuelPort` |
| **Infrastructure (Adapters)** | Concrete implementations for external systems, databases, or HTTP endpoints. | `FlightRestController`, `JpaFlightRepository` |

---

## 4. Decision Orchestration Flow

The primary capability of the application is evaluating flight safety parameters. The decision engine acts as an orchestrator that invokes calculations from other bounded contexts. 

The sequence diagram below demonstrates how the `EvaluateFlightUseCase` coordinates the entire evaluation process:

<img width="1435" height="699" alt="Sequence Diagram" src="https://github.com/user-attachments/assets/999a6194-b1cf-4950-8640-f1462c8191b7" />

---

## 5. Domain Class Model and Relationships

Common objects are shared via a Shared Kernel (`common`), while specific result schemas remain tightly encapsulated within their respective bounded contexts:

<img width="1478" height="601" alt="Class Diagram" src="https://github.com/user-attachments/assets/afe797c9-b035-480c-af9b-303fa43c2b02" />

---

## 6. Dependency Injection & Configuration

Spring Beans are wired **explicitly** within configuration files under the `config` package. This completely decouples the core domain and application layers from the Spring Framework dependencies:

* **`DecisionBeanConfig.java`**: Wires `DecisionEngine` and `EvaluateFlightUseCase`.
* **`FuelBeanConfig.java`**: Wires `FuelCalculator` and `CalculateFuelRequirementUseCase`.
* **`NavigationBeanConfig.java`**: Wires `HaversineCalculator`, `RouteDistanceCalculator`, and `CalculateRouteDistanceUseCase`.
* **`PerformanceBeanConfig.java`**: Wires `RunWayLengthRule`, `TakeoffPerformanceCalculator`, and `CalculateTakeoffPerformanceUseCase`.

```
