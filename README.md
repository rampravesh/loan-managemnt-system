# Loan Management System

## Introduction

This project is a Loan Management System designed to handle various aspects of loan products. It is structured as a multi-module Maven project, with different services handling specific functionalities.

## Modules

The system is composed of the following modules:

### `library`

This module serves as a common library for the other services. It contains:
*   Core domain models (JPA Entities) such as `LoanPlans`, `BaseInterestRates`, and `LoanPlansHistory`.
*   Data Transfer Objects (DTOs) like `LoanPlansDTO`, `BaseInterestRatesDTO` for API communication.
*   Spring Data JPA repositories for data access.

### `loan-plans-service`

This is a Spring Boot microservice responsible for managing loan plans and base interest rates.
*   It provides REST APIs for creating, reading, updating, and deleting (CRUD) loan plans and interest rates.
*   It utilizes the `library` module for its data persistence layer.
*   API documentation is available via Swagger/OpenAPI (typically at `/swagger-ui.html` when the service is running).

### `loan-application-service`

This service is intended to handle new loan applications and manage the loan application lifecycle.
*   **Note:** This service is currently under development, and its functionalities will be expanded in future updates.

## How to Build

To build the entire project, navigate to the root directory and run the following Maven command:

```bash
mvn clean install
```

## How to Run

Currently, the main runnable service is the `loan-plans-service`.

To run the `loan-plans-service`:

1.  Navigate to the `loan-plans-service` directory:
    ```bash
    cd loan-plans-service
    ```
2.  Run the service using Maven:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, you can build the JAR file (`mvn package`) and then run it using `java -jar target/plans-service-1.0.0-SNAPSHOT.jar` (the JAR filename might vary based on the version).

Once the service is running (typically on port 8080), you can access the Swagger UI for API documentation at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Technologies Used

*   Java (JDK 1.8)
*   Spring Boot
*   Spring Data JPA
*   Hibernate
*   Maven
*   MySQL (as a target database for `loan-plans-service`)
*   H2 Database (for testing/runtime in `loan-plans-service`)
*   Lombok
*   ModelMapper
*   Springdoc OpenAPI (for Swagger UI)
*   JUnit 5

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue.

(Further details on contribution guidelines can be added here in the future.)

## License

This project is currently unlicensed. Please add a suitable open source license (e.g., MIT, Apache 2.0) if desired.
