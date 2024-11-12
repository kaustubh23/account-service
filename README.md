
# Current Account API

## Overview
The **Current Account  API** is a Spring Boot RESTful service designed for managing customer accounts and transactions. It supports account creation, initial credit deposits, and provides detailed customer information, including balances and transaction history.

This API is built with best practices in mind, offering features such as structured response handling, validation, exception handling, and integration with Swagger for API documentation.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Error Handling](#error-handling)
- [Project Structure](#project-structure)
- [Tests](#tests)
- [ Remaining Tasks](#contributing)


## Features
- **Customer Account Management**: Create and manage customer accounts.
- **Transaction Processing**: Initialize transactions on account creation with configurable initial credit.
- **Customer Info Retrieval**: Retrieve account balances and transaction history for each customer.
- **Validation and Error Handling**: Comprehensive input validation and standardized error responses.
- **Swagger Integration**: API documentation and testing capabilities via Swagger UI.

## Prerequisites
- **Java**: 17 or later
- **Maven**: 3.6.0 or later
- **Spring Boot**: 3.0.0 or later
- **Database**:  H2 for in-memory testing

## Installation
1. **Clone the Repository**:
    ```bash
    git clone https://github.com/kaustubh23/account-service.git
    ```

2. **Configure Database Settings**:
   Update `src/main/resources/application.properties` with your database configuration.

3. **Build the Project**:
    ```bash
    mvn clean install
    ```

4. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```
   The API will be available at `http://localhost:8080`.

## Configuration
The application’s default configuration is in `src/main/resources/application.properties`. Configure database and other settings as follows:

```properties
# Database Configuration
spring.datasource.url=http://localhost:8080/h2-console
jdbc URL: jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
test userId 1 and 2

# Swagger Configuration
springdoc.api-docs.enabled=true
http://localhost:8080/swagger-ui.html
```

For in-memory testing, you can use the H2 database configuration.

## Usage
### Endpoints
1. **Open Account**: Create a new account with an optional initial credit.
    - **POST** `/api/account/create`
    - **Request Body**:
      ```json
      {
        "customerId": 1,
        "initialCredit": 100.0
      }
      ```

2. **Get Customer Information**: Retrieve customer details, including balance and transaction history.
    - **GET** `/api/account/{customerId}/info`

## API Documentation
API documentation is available through Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```
Docker image command
mvn spring-boot:build-image

The OpenAPI (Swagger) documentation provides detailed information on each endpoint, including request/response examples, parameter descriptions, and error handling.

## Error Handling
The API uses structured error handling to return meaningful messages for various error conditions:
- **400 Bad Request**: Invalid inputs or missing required fields.
- **404 Not Found**: Customer or account not found.
- **500 Internal Server Error**: Unhandled exceptions or server errors.

All error responses follow a standard JSON format:
```json
{
  "status": "ERROR",
  "message": "Detailed error message",
  "data": null
}
```

## Project Structure
```
src
├── main
│   ├── java
│   │   └── account.service.current
│   │       ├── controller         # REST controllers for handling requests
│   │       ├── dto                # Data Transfer Objects (DTOs) for API communication
│   │       ├── entity             # Entity classes for database modeling
│   │       ├── exception          # Custom exceptions and error handling
│   │       ├── repository         # Spring Data JPA repositories
│   │       ├── service            # Service layer for business logic
│   │       └── mapper             # Mapper classes to transform entities to DTOs
│   └── resources
│       ├── application.properties # Application configuration
└── test                           # Unit and integration tests
```

## Tests
The project includes unit and integration tests to ensure reliable functionality:
- **Unit Tests**: For service and repository layers.
- **Integration Tests**: For testing the full request-response cycle in `AccountControllerIntegrationTest`.

To run tests:
```bash
mvn test
```


## Remaining Tasks
Security implementation

Central configuration: Application profileing

Monitoring and deep code cleaning and make it more readable.

Converting services into seperate microserivces and implement Event Driven Architecure

Add more test scenarios

Frontend for the application
