# Sp Execution tool

A Spring Boot application for executing stored procedures and managing database operations project.

## Features
- RESTful API endpoints for executing stored procedures
- JSON request/response handling
- SQL Database connection
- Configurable via `application.properties`

## Technologies Used
- Java 17
- Spring Boot

## Project Structure
- `controller/` - REST controllers
- `service/` - Business logic
- `repository/` - Database access layer
- `config/` - Database and template configuration
- `model/` - Data models
- `common/` - Constants and shared utilities

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### Configuration
Edit `src/main/resources/application.properties` to set up database and other properties.

## API Endpoints
See controller classes in `src/main/java/com/maersk/spexecution/controller/` for available endpoints and request/response formats.

## Testing
Run tests with:
```bash
mvn test
```


