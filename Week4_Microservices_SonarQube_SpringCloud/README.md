# Week 4 – Microservices, SonarQube & Spring Cloud

---

## Theory

### Enterprise Applications

Large organizations like banks offer a wide range of products and services — savings accounts, loans, insurance, credit cards, forex, and more. The systems that manage the service levels of these organizations are called **Enterprise Applications**.

**Activity:** Visit [https://www.airtel.in/](https://www.airtel.in/) and list all products and services offered. Discuss the findings as a group.

---

### Monolithic Services

Consider a common banking operation: **Get Account Balance**. This single operation is consumed by many different actors and systems:

- Customer via mobile app
- Bank teller via internal portal
- Customer via net banking
- IVR system reading balance aloud
- Customer service representative
- Batch job for EMI deduction
- Customer via ATM
- SMS notification system
- ...and more

A company that packages **all such operations** into a single RESTful Web Service application is building a **Monolithic Service**.

#### The Problem with Monoliths — A Real Scenario

> After launch, during a festival shopping season, a memory leak in the "Get Account Balance" service caused the entire application server to run out of memory. The server became unresponsive, which meant:
> - Loan agents could not submit applications
> - Insurance agents could not process closures
> - Customers could not report stolen credit cards
>
> The entire server had to be restarted, and all services were down for 2–3 hours.

**Definition:** A large number of critical enterprise services hosted as a single web application is called a **Monolithic Service**.

#### Drawbacks of Monolithic Services

- Everything is packaged in one EAR/WAR. A performance or memory leak in one service brings down **all** services.
- No flexibility to use a different technology stack for individual services.
- Difficult to scale a single service independently.
- Any change requires redeploying the entire application.

---

### Microservices

Instead of one monolith, services are **split into multiple independent services**. Each microservice handles a specific business capability and runs as its own deployable unit.

**Definition:** An architectural style where an application is structured as a collection of small, independently deployable services, each running in its own process and communicating via lightweight APIs.

#### Advantages of Microservices

| Advantage | Description |
|---|---|
| Decentralized | Each service owns its data and logic |
| Independent | Failure of one service does not bring down others |
| Single Responsibility | Each service does one thing well |
| Agility | Teams can develop, deploy, and scale services independently |
| Scalable | Add more instances of a specific service without touching others |
| Easier fault isolation | Quickly identify which service is failing |
| Developer-friendly | New developers can understand one small service at a time |
| Continuous Delivery | Enables frequent, low-risk deployments |

#### Challenges of Microservices

- Developing distributed systems can be complex
- Initial setup and infrastructure overhead is higher
- Inter-service communication requires careful design (REST, messaging, etc.)
- Distributed tracing and monitoring require additional tooling

---

## Hands-On: Creating Account and Loan Microservices

### Overview

Two independent Spring Boot microservices are implemented:

| Service | Port | Endpoint | Description |
|---|---|---|---|
| Account | 8080 | `GET /accounts/{number}` | Returns account details |
| Loan | 8081 | `GET /loans/{number}` | Returns loan details |

Both services are simple RESTful web services with **no backend/database connectivity** — they return dummy responses.

---

### Project Structure

```
microservices/
├── account/                          <- Account Microservice (port 8080)
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/cognizant/account/
│       │   │   ├── AccountApplication.java       <- Spring Boot entry point
│       │   │   ├── controller/
│       │   │   │   └── AccountController.java    <- REST controller
│       │   │   └── model/
│       │   │       └── Account.java              <- Model class
│       │   └── resources/
│       │       └── application.properties        <- server.port=8080
│       └── test/
│           └── java/com/cognizant/account/
│               └── AccountApplicationTests.java
│
└── loan/                             <- Loan Microservice (port 8081)
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/cognizant/loan/
        │   │   ├── LoanApplication.java          <- Spring Boot entry point
        │   │   ├── controller/
        │   │   │   └── LoanController.java       <- REST controller
        │   │   └── model/
        │   │       └── Loan.java                 <- Model class
        │   └── resources/
        │       └── application.properties        <- server.port=8081
        └── test/
            └── java/com/cognizant/loan/
                └── LoanApplicationTests.java
```

---

### Step-by-Step Setup (using Spring Initializr)

1. Go to [https://start.spring.io/](https://start.spring.io/)
2. Fill in the form:
   - **Group:** `com.cognizant`
   - **Artifact:** `account` (or `loan`)
3. Add dependencies:
   - `Spring Boot DevTools` (Developer Tools)
   - `Spring Web` (Web)
4. Click **Generate** and download the zip
5. Extract and place the folder inside the `microservices/` directory
6. Build using: `mvn clean package`
7. Import into Eclipse as a Maven project

---

### Account Microservice

**Endpoint:** `GET /accounts/{number}`

**Sample Response:**
```json
{
  "number": "00987987973432",
  "type": "savings",
  "balance": 234343
}
```

**Test:** Open browser or Postman → `http://localhost:8080/accounts/00987987973432`

---

### Loan Microservice

**Endpoint:** `GET /loans/{number}`

**Sample Response:**
```json
{
  "number": "H00987987972342",
  "type": "car",
  "loan": 400000,
  "emi": 3258,
  "tenure": 18
}
```

**Test:** Open browser or Postman → `http://localhost:8081/loans/H00987987972342`

---

### Port Configuration

When both services run simultaneously, they must use different ports.

The Account service uses the default port **8080**.  
The Loan service is configured to use **8081** via `application.properties`:

```properties
# loan/src/main/resources/application.properties
server.port=8081
```

> **Note:** Without this setting, launching the Loan service while Account is running will fail with:  
> `Web server failed to start. Port 8080 was already in use.`

---

### Running the Services

**In Eclipse:**
1. Run `AccountApplication.java` as a Spring Boot App → starts on port 8080
2. Run `LoanApplication.java` as a Spring Boot App → starts on port 8081
3. Switch between consoles using the monitor icon in the Eclipse Console view

**Via Maven (command line):**
```bash
# Terminal 1 - Account service
cd microservices/account
mvn spring-boot:run

# Terminal 2 - Loan service
cd microservices/loan
mvn spring-boot:run
```

---

## Key Takeaways

- A **Monolithic** architecture packages all services in one deployable unit — a single failure can affect everything.
- **Microservices** split functionality into independent, deployable units — failures are isolated and scaling is targeted.
- Each microservice runs on its own port and can be developed, deployed, and scaled independently.
- Spring Boot makes it straightforward to create lightweight, standalone microservices.
