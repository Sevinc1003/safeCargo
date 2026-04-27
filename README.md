# Cargora

Cargora is a Spring Boot REST API for cargo and parcel delivery management. It covers the core delivery workflow: account registration and login, JWT authentication, role-based operations, package creation and tracking, package history, pickup-point management, and balance top-ups through a fake Stripe-style payment service.

The Maven application is located in the `cargora` directory.

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

## Authentication

### Register

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "username": "user@example.com",
  "password": "Password1",
  "pin": "1234567",
  "fullname": {
    "name": "Ali",
    "surname": "Aliyev"
  }
}
```

### Login

```http
GET /auth/login
Content-Type: application/json
```

```json
{
  "username": "user@example.com",
  "password": "Password1"
}
```

Both endpoints return:

```json
{
  "token": "jwt-token",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

Use the token on protected endpoints:

```http
Authorization: Bearer jwt-token
```

## API Endpoints

### Auth

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/auth/register` | Register a new account |
| `GET` | `/auth/login` | Login and receive a JWT token |

### Packages

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/packages/create-new` | Create a package. Requires `ADMIN` or `EMPLOYEE` role. |
| `GET` | `/packages/{id}` | Get package details by database id |
| `GET` | `/packages/tracking-code/{code}` | Track a package by external or internal tracking code |
| `GET` | `/packages/of-user/{PIN}` | List packages by user PIN |
| `GET` | `/packages/status/{status}` | List packages by status |
| `PATCH` | `/packages/update-weight` | Update package weight and shipping fee |
| `PATCH` | `/packages/update-destinationBranch` | Update destination pickup point |
| `GET` | `/packages/filter-by` | Filter packages by optional query parameters |

Supported package statuses:

```text
DECLARED
AT_FOREIGN_WAREHOUSE
ON_THE_WAY
DECLARATION
ARRIVED
DELIVERED
```

Create package example:

```json
{
  "userId": 1,
  "destinationBranchId": 1,
  "trackingNumber": "TRK123456",
  "weight": 2.5
}
```

Update weight example:

```json
{
  "packageId": 1,
  "weight": 3.2
}
```

Filter example:

```http
GET /packages/filter-by?pin=1234567&status=ARRIVED&minWeight=1&maxWeight=5
```

### Package History

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/package-history/{packageId}` | Get status history for a package |

### Users

| Method | Path | Description |
| --- | --- | --- |
| `PUT` | `/user/home-address` | Update the authenticated user's home address |
| `PUT` | `/user/pickup-point` | Update the authenticated user's pickup point |
| `PUT` | `/user/{userId}/update-address` | Update a user's address. Requires `ADMIN` or `EMPLOYEE` role. |
| `PUT` | `/user/{userId}/update-pickup-point` | Update a user's pickup point. Requires `ADMIN` or `EMPLOYEE` role. |
| `PATCH` | `/user/disable-user/{userId}` | Disable a user. Requires `ADMIN` or `EMPLOYEE` role. |
| `PATCH` | `/user/enable-user/{userId}` | Enable a user. Requires `ADMIN` or `EMPLOYEE` role. |

### Balance

| Method | Path | Description |
| --- | --- | --- |
| `PATCH` | `/balance/top-up` | Add funds to a user's balance |

Top-up example:

```json
{
  "userId": 1,
  "amount": 25.00,
  "cardNumber": "4111111111111111",
  "cvv": "123"
}
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
