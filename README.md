# Cargora

Cargora is a Spring Boot REST API for cargo and parcel delivery management. It covers the core delivery workflow: account registration and login, JWT authentication, role-based operations, package creation and tracking, package history, pickup-point management, and balance top-ups through a fake Stripe-style payment service.

The Maven application is located in the `cargora` directory.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Configuration](#configuration)
- [Run Locally](#run-locally)
- [Entity Diagram](docs/entity-diagram.png)
- [Authentication](docs/auth.md)
- [API Endpoints](docs/api-endpoints.md)
- [Tests](#tests)
- [Notes](#notes)

## Features

- JWT-based registration and login
- Role-based access for users, employees, and admins
- Package creation, lookup, filtering, and tracking
- Package status history by package id
- Package weight updates with shipping fee calculation
- Destination pickup-point updates
- User home address and pickup-point management
- User enable/disable actions for admins and employees
- Balance top-up flow with a fake payment provider
- Centralized validation and API error responses

## Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Security
- Spring Data JPA / Hibernate
- MySQL
- JWT with `java-jwt`
- Lombok
- Maven Wrapper

## Project Structure

```text
safeCargo/
  cargora/
    pom.xml
    mvnw
    mvnw.cmd
    src/
      main/
        java/az/cargora/cargora/
          controller/    REST API controllers
          dto/           Request and response models
          entity/        JPA entities
          enums/         Domain enums
          exception/     Error handling
          payment/       Fake payment provider models
          repository/    Spring Data repositories
          security/      JWT and Spring Security configuration
          service/       Business logic
        resources/
          application.properties
      test/
        java/az/cargora/cargora/
```

## Requirements

- JDK 21
- MySQL running locally
- Maven, or the included Maven Wrapper

## Configuration

Create a local MySQL database:

```sql
CREATE DATABASE cargora;
```

The local datasource is configured in `cargora/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cargora
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
```

For shared environments, keep database credentials and the JWT secret outside source control by moving them to environment-specific configuration.

## Run Locally

From the repository root:

```bash
cd cargora
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
cd cargora
.\mvnw.cmd spring-boot:run
```

The API starts at:

```text
http://localhost:8080
```


## Tests

Run tests from the Maven project directory:

```bash
cd cargora
./mvnw test
```

On Windows PowerShell:

```powershell
cd cargora
.\mvnw.cmd test
```

## Notes

- Hibernate currently uses `spring.jpa.hibernate.ddl-auto=update` for local development.
- Shipping fee calculation is currently `weight * 5`.
- Internal tracking codes are generated with the `EXP-` prefix.
- Some controller tests may still target older endpoint or service shapes and may need updates as the API evolves.
