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

Layer	Path Component	Key Responsibilities	Examples
API	.../<context>/api/	Exposes REST endpoints, handles serialization, maps HTTP requests to DTOs.	PerformanceController, EvaluateFlightRequest
Application	.../<context>/application/	Orchestrates flow, defines repository interfaces (ports), coordinates domain actions.	EvaluateFlightUseCase, AircraftRepository (port)
Domain	.../<context>/domain/	Holds pure business logic, rules, value objects, and domain services. Framework-independent.	DecisionEngine, FuelCalculator, RunWayLengthRule
Infrastructure	.../<context>/infrastructure/	Implements repository interfaces (adapters) using maps, file storage, or external services.	InMemoryPerformanceAircraftRepository

