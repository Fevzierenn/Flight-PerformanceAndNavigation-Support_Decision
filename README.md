# Flight-PerformanceAndNavigation-Support_Decision
Input for a flight:
↓
Performance → Can it take off?
Navigation → Is the route & alternative suitable?
Fuel → Is there enough fuel? Risk → General decision
↓
Flight: SAFE / RISKY / NOT_ALLOWED

Project Structure and Relationship Diagram
This document presents a comprehensive, precise, and structural overview of the AeroDecision (Flight Performance & Navigation Support Decision) platform. It details the system architecture, file layout, bounded context separation, sequence workflows, and class relationships using standard visual modeling.

<img width="1137" height="142" alt="image" src="https://github.com/user-attachments/assets/3d944565-8fb5-43c3-8da7-9705f5ad16c2" />

2. Directory Layout & Bounded Contexts
The codebase is organized into Bounded Contexts under the primary package prefix com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform. Each bounded context represents a distinct functional domain.

<img width="1432" height="258" alt="image" src="https://github.com/user-attachments/assets/0f3e5e44-f141-44ea-92eb-ff5bbb46605d" />
3. Hexagonal (Ports & Adapters) Layering in Bounded Contexts
Each bounded context (except common and config) strictly follows Clean/Hexagonal Architecture layers. The diagram below illustrates a representative context (e.g., performance), showing the separation between driving/driven adapters, application boundaries, and domain cores:
<img width="1469" height="449" alt="image" src="https://github.com/user-attachments/assets/87cbbfe7-a73f-43b2-8d12-992b6adb2172" />

Below is the summary of the purpose of each layer:

<img width="1094" height="379" alt="image" src="https://github.com/user-attachments/assets/f590b4b6-e094-41b0-b03c-d72876728ba0" />

4. Decision Orchestration Flow
The core capability of the application is evaluating flight safety parameters. The decision engine acts as an orchestrator that invokes calculations from other bounded contexts. The sequence diagram below shows how the 
EvaluateFlightUseCase
 coordinates the evaluation process:
<img width="1435" height="699" alt="image" src="https://github.com/user-attachments/assets/999a6194-b1cf-4950-8640-f1462c8191b7" />

5. Domain Class Model and Relationships
Common objects are shared via a Shared Kernel (common), while specific result schemas are contained inside their respective contexts:

<img width="1478" height="601" alt="image" src="https://github.com/user-attachments/assets/afe797c9-b035-480c-af9b-303fa43c2b02" />


6. Dependency Injection & Configuration
Spring Beans are wired explicitly in configuration files under the 
config
 package, decoupling implementation from configuration:

DecisionBeanConfig.java: Wires DecisionEngine and EvaluateFlightUseCase.
FuelBeanConfig.java: Wires FuelCalculator and CalculateFuelRequirementUseCase.
NavigationBeanConfig.java: Wires HaversineCalculator, RouteDistanceCalculator, and CalculateRouteDistanceUseCase.
PerformanceBeanConfig.java: Wires RunWayLengthRule, TakeoffPerformanceCalculator, and CalculateTakeoffPerformanceUseCase.
