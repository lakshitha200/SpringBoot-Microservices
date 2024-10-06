# Spring Boot Full Stack Shopping Cart Microservices Project Architecture

![Screenshot_20241007-002653_Flowdia Diagrams](https://github.com/user-attachments/assets/c6c386f7-a055-4ba4-be55-87bd3a62cade)


- Back-end : Spring Boot
- Front-end : Angular

## Services Breakdown:

### Customer Service
- Function: Handles customer-related data (crud operations).
- Database: MySQL

### Order Service
- Function: Manages order creation, order details, and interacts with Product, Customer, Notification and Payment services.
- Database: MySQL
  
### Product Service
- Function: Manages product data, including creation, updates, and availability checks.
- Database: MongoDB

### Payment Service
- Function: Manages payment processing for orders.
- Database: MySQL

### Notification Service
- Function: Sends notifications to customers, triggered asynchronously using Kafka.
- Kafka message streaming

### Auth Service
- Function: Provides security features, including authentication and authorization. (Spring Security + JWT)
- Database: MySQL

## Technologies and Services Used:
- Spring Boot
- Spring Cloud
- Eureka Server (Service Discovery) - Acts as a service registry where microservices register themselves and discover others.
- Feign Client - To enable easier inter-service communication in a RESTful manner.
- Spring Cloud Config Server with github - Centralized configuration management for all microservices.
- Spring Cloud Gateway (API Gateway) - A single-entry point that routes requests to appropriate services.
- Resilience4j - Provides resilience patterns such as Circuit Breakers, Bulkhead, Retry, and Rate Limiting.
- Kafka - Used for asynchronous communication to send notifications when an order is placed or completed, improving non-blocking communication between services.
- MongoDB
- MySQL
- Docker - To containerize each microservice, making deployment easier and ensuring consistency across environments (All services are running in docker containers).
- Docker Compose - To orchestrate multiple containers (microservices, databases, Kafka) and manage their dependencies in the development environment.
- Spring Boot Actuator - To expose health, metrics, and other service information to help in monitoring and debugging.
- Testcontainers - For integration testing
- Angular - For Front-end
