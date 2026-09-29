# E-Commerce Microservices

A backend e-commerce application built using **Java 17**, **Spring Boot**, **Spring Cloud**, **PostgreSQL**, **Apache Kafka**, **Netflix Eureka**, and **Spring Cloud Gateway**.

The project demonstrates a microservices-based architecture in which individual business capabilities such as users, products, orders, inventory, payments, and notifications are implemented as independent Spring Boot services.

---

## Architecture

```text
                         ┌────────────────────┐
                         │       Client       │
                         └──────────┬─────────┘
                                    │
                                    ▼
                         ┌────────────────────┐
                         │    API Gateway     │
                         │      :8080         │
                         └──────────┬─────────┘
                                    │
             ┌──────────────────────┼──────────────────────┐
             │                      │                      │
             ▼                      ▼                      ▼
      ┌─────────────┐       ┌─────────────┐       ┌─────────────┐
      │ User Service│       │Product Svc  │       │ Order Svc   │
      │    :8081    │       │    :8082    │       │    :8083    │
      └──────┬──────┘       └──────┬──────┘       └──────┬──────┘
             │                      │                      │
             ▼                      ▼                      ▼
         PostgreSQL             PostgreSQL             PostgreSQL
                                                            │
                                                            │ Order Event
                                                            ▼
                                                       ┌───────────┐
                                                       │   Kafka   │
                                                       │   :9092   │
                                                       └─────┬─────┘
                                                             │
                                                             ▼
                                                   ┌──────────────────┐
                                                   │Notification Svc  │
                                                   │      :8086       │
                                                   └──────────────────┘

             ┌──────────────────┐       ┌──────────────────┐
             │ Payment Service  │       │Inventory Service │
             │      :8084       │       │      :8085       │
             └──────────────────┘       └────────┬─────────┘
                                                 │
                                                 ▼
                                             PostgreSQL


                  ┌─────────────────────────────┐
                  │ Eureka Discovery Server    │
                  │           :8761            │
                  └─────────────────────────────┘
```

---

# Microservices

## User Service

Responsible for user-related operations.

**Port**

```text
8081
```

Main technologies:

- Spring Boot
- Spring Web MVC
- Spring Security
- Spring Data JPA
- PostgreSQL
- Eureka Client

Database:

```text
user_db
```

The service registers itself with Eureka as:

```text
user-service
```

---

## Product Service

Responsible for managing product information.

**Port**

```text
8082
```

Main technologies:

- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Lombok

Database:

```text
product_db
```

The service registers itself as:

```text
product-service
```

---

## Order Service

Responsible for processing and storing customer orders.

**Port**

```text
8083
```

Main technologies:

- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Jackson
- Lombok

Database:

```text
order_db
```

The Order Service acts as a Kafka producer.

When an order is processed, the service can publish an order event to Kafka for asynchronous processing by other services.

Example flow:

```text
Create Order
     │
     ▼
Order Service
     │
     ├── Save Order
     │
     ▼
 PostgreSQL
     │
     │ Publish Event
     ▼
   Kafka
```

---

## Payment Service

Responsible for payment-related operations.

**Port**

```text
8084
```

The current service provides the foundation for separating payment processing from the rest of the e-commerce platform.

Main technologies:

- Spring Boot
- Spring Web MVC
- Lombok

---

## Inventory Service

Responsible for maintaining product inventory information.

**Port**

```text
8085
```

Main technologies:

- Spring Boot
- Spring Data JPA
- Spring Web MVC
- PostgreSQL
- Lombok

Database:

```text
inventory_db
```

---

## Notification Service

Consumes asynchronous order events from Apache Kafka.

**Port**

```text
8086
```

Main technologies:

- Spring Boot
- Spring Kafka
- Spring Web
- Jackson
- Lombok

Kafka consumer group:

```text
notification-group
```

The service listens for order events and processes them independently of the Order Service.

Example:

```text
Order Service
     │
     │ Publish Order Event
     ▼
   Kafka
     │
     │ Consume Event
     ▼
Notification Service
```

This demonstrates asynchronous, event-driven communication between microservices.

---

# API Gateway

The project uses **Spring Cloud Gateway** as the application's central API entry point.

**Port**

```text
8080
```

The gateway discovers services using Eureka and forwards incoming requests to the appropriate backend service.

Configured routes:

| Request Path | Destination |
|---|---|
| `/api/users/**` | `user-service` |
| `/api/products/**` | `product-service` |
| `/api/orders/**` | `order-service` |
| `/api/inventory/**` | `inventory-service` |
| `/api/payments/**` | `payment-service` |

Instead of calling individual services directly:

```text
http://localhost:8081
http://localhost:8082
http://localhost:8083
```

clients can communicate through:

```text
http://localhost:8080
```

For example:

```text
http://localhost:8080/api/products
```

The gateway resolves the target service through Eureka.

---

# Service Discovery

The project uses **Netflix Eureka** for service registration and discovery.

The discovery server runs on:

```text
http://localhost:8761
```

Eureka dashboard:

```text
http://localhost:8761/
```

Microservices register themselves with Eureka.

Conceptually:

```text
User Service ────────┐
Product Service ─────┤
Order Service ───────┤
Inventory Service ───┤
                     ▼
               Eureka Server
                     ▲
                     │
                 API Gateway
```

This allows the API Gateway to locate services by their logical service name rather than hard-coded ports.

---

# Event-Driven Architecture

The application uses **Apache Kafka** for asynchronous communication.

Currently:

```text
Order Service
     │
     │ publishes
     ▼
 Order Event
     │
     ▼
   Kafka
     │
     │ consumes
     ▼
Notification Service
```

This keeps notification processing separate from the synchronous order-processing flow.

Benefits include:

- Loose coupling between services
- Asynchronous processing
- Independent service scaling
- Better separation of responsibilities

---

# Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | Microservice framework |
| Spring Web / MVC | REST APIs |
| Spring Cloud Gateway | API Gateway |
| Netflix Eureka | Service discovery |
| Spring Data JPA | Database persistence |
| Hibernate | ORM |
| PostgreSQL | Relational database |
| Apache Kafka | Event streaming |
| Spring Kafka | Kafka producer/consumer integration |
| Spring Security | Security foundation |
| Maven | Build and dependency management |
| Lombok | Boilerplate reduction |
| Docker Compose | Kafka development environment |
| Zookeeper | Kafka coordination |

---

# Project Structure

```text
ecommerce-microservices/
│
├── api-gateway/
│   ├── src/
│   └── pom.xml
│
├── discovery-server/
│   ├── src/
│   └── pom.xml
│
├── user-service/
│   ├── src/
│   └── pom.xml
│
├── product-service/
│   ├── src/
│   └── pom.xml
│
├── order-service/
│   ├── src/
│   └── pom.xml
│
├── payment-service/
│   ├── src/
│   └── pom.xml
│
├── inventory-service/
│   ├── src/
│   └── pom.xml
│
├── notification-service/
│   ├── src/
│   └── pom.xml
│
├── docker-compose.yml
└── README.md
```

---

# Service Ports

| Component | Port |
|---|---:|
| API Gateway | `8080` |
| User Service | `8081` |
| Product Service | `8082` |
| Order Service | `8083` |
| Payment Service | `8084` |
| Inventory Service | `8085` |
| Notification Service | `8086` |
| Eureka Server | `8761` |
| Kafka | `9092` |
| Zookeeper | `2181` |

---

# Databases

Several services use separate PostgreSQL databases.

```text
User Service
    │
    ▼
 user_db


Product Service
    │
    ▼
 product_db


Order Service
    │
    ▼
 order_db


Inventory Service
    │
    ▼
inventory_db
```

Keeping data responsibilities separated by service helps reduce direct database coupling.

---

# Prerequisites

Before running the project, install:

- Java 17
- Maven
- PostgreSQL
- Docker
- Docker Compose
- Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Verify Docker:

```bash
docker --version
```

---

# Clone the Repository

```bash
git clone https://github.com/siri-chandanak/ecommerce-microservices.git

cd ecommerce-microservices
```

---

# PostgreSQL Setup

Create the required databases:

```sql
CREATE DATABASE user_db;

CREATE DATABASE product_db;

CREATE DATABASE order_db;

CREATE DATABASE inventory_db;
```

Update each service's database configuration if your PostgreSQL username, password, hostname, or port is different.

A safer configuration approach is to use environment variables instead of committing database credentials into source control.

For example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

---

# Start Kafka

Kafka and Zookeeper are provided through Docker Compose.

Run:

```bash
docker compose up -d
```

Check containers:

```bash
docker compose ps
```

Kafka is available at:

```text
localhost:9092
```

Zookeeper is available at:

```text
localhost:2181
```

Stop Kafka:

```bash
docker compose down
```

---

# Running the Application

The recommended startup order is:

```text
1. PostgreSQL
2. Kafka / Zookeeper
3. Discovery Server
4. User Service
5. Product Service
6. Order Service
7. Payment Service
8. Inventory Service
9. Notification Service
10. API Gateway
```

---

## Start Discovery Server

```bash
cd discovery-server

mvn spring-boot:run
```

Open:

```text
http://localhost:8761
```

---

## Start User Service

```bash
cd user-service

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8081
```

---

## Start Product Service

```bash
cd product-service

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8082
```

---

## Start Order Service

```bash
cd order-service

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8083
```

---

## Start Payment Service

```bash
cd payment-service

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8084
```

---

## Start Inventory Service

```bash
cd inventory-service

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8085
```

---

## Start Notification Service

```bash
cd notification-service

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8086
```

---

## Start API Gateway

```bash
cd api-gateway

mvn spring-boot:run
```

Runs on:

```text
http://localhost:8080
```

---

# Verify Service Discovery

After starting the services, open:

```text
http://localhost:8761
```

Registered services should appear in the Eureka dashboard.

Typical services include:

```text
API-GATEWAY
USER-SERVICE
PRODUCT-SERVICE
ORDER-SERVICE
```

Services configured as Eureka clients can then be resolved using their service names.

---

# API Request Flow

A typical API request travels through:

```text
Client
   │
   ▼
API Gateway
   │
   ▼
Eureka Service Discovery
   │
   ▼
Target Microservice
   │
   ▼
Database
```

For example:

```text
GET /api/products
       │
       ▼
 API Gateway :8080
       │
       ▼
product-service
       │
       ▼
 PostgreSQL
```

---

# Order Event Flow

An order can trigger an asynchronous Kafka event:

```text
Client
   │
   │ Create Order
   ▼
API Gateway
   │
   ▼
Order Service
   │
   ├─────────────► PostgreSQL
   │
   │
   └── Publish Order Event
              │
              ▼
            Kafka
              │
              ▼
     Notification Service
              │
              ▼
      Process Notification
```

This demonstrates both synchronous REST communication and asynchronous event-driven processing within the same system.

---

# Build Services

Each service is an independent Maven project.

Example:

```bash
cd product-service

mvn clean package
```

Run tests:

```bash
mvn test
```

Run the generated JAR:

```bash
java -jar target/*.jar
```

Repeat the same process for the other services.

---

# Key Microservices Concepts Demonstrated

This project demonstrates:

- Service decomposition
- REST API development
- Independent Spring Boot services
- API Gateway pattern
- Service registration
- Service discovery
- Client-side service resolution
- Database-per-service concepts
- Event-driven communication
- Kafka producer/consumer architecture
- Asynchronous processing
- Spring Data JPA
- PostgreSQL persistence
- Spring Security foundation
- Maven dependency management

---

# Current Capabilities

- Separate user service
- Separate product service
- Order processing service
- Inventory management service
- Payment service
- Notification service
- Central API Gateway
- Eureka service discovery
- PostgreSQL persistence
- Kafka event publishing
- Kafka event consumption
- Docker Compose for Kafka and Zookeeper

---

# Future Improvements

Possible improvements for making the project more production-oriented:

- JWT authentication and authorization across services
- Role-based access control
- Centralized configuration using Spring Cloud Config
- Dockerize all microservices
- Add PostgreSQL containers to Docker Compose
- Kubernetes manifests
- Helm charts
- Resilience4j circuit breakers
- Retry and timeout policies
- Distributed tracing
- OpenTelemetry
- Prometheus metrics
- Grafana dashboards
- Centralized logging
- Dead-letter topics
- Kafka retry handling
- Schema validation
- Transactional Outbox Pattern
- API documentation using OpenAPI / Swagger
- Integration tests
- Testcontainers
- CI/CD using GitHub Actions
- Secrets management
- Rate limiting
- Health checks
- Kubernetes readiness and liveness probes
- Horizontal Pod Autoscaling

---

# Production Considerations

This repository is currently a learning and portfolio-oriented microservices implementation.

Before using a similar architecture in production:

- Never commit database credentials to Git.
- Store credentials using environment variables or a secrets manager.
- Use database migrations such as Flyway or Liquibase.
- Containerize all services.
- Add authentication and service-to-service authorization.
- Add monitoring and distributed tracing.
- Configure Kafka for durability and replication.
- Add retry and dead-letter handling.
- Add automated tests.
- Add health and readiness endpoints.
- Add CI/CD pipelines.
- Use infrastructure orchestration such as Kubernetes.

---

# Repository

```text
https://github.com/siri-chandanak/ecommerce-microservices
```

---

## License

This project is intended for educational, experimentation, and portfolio purposes.

---

Built with **Java · Spring Boot · Spring Cloud · PostgreSQL · Kafka · Eureka · Maven**
