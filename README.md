# Hotel API

RESTful API for hotel management: hotel listing/search, detailed hotel profiles, hotel creation, amenity management, and parameter-based histograms.

## Technology Stack
- Java 21 
- Maven
- Spring Boot 3.5.16, Spring Web (MVC)
- Spring Data JPA, Hibernate, Liquibase
- H2 (in-memory)
- PostgreSQL, MySQL (optional)
- MapStruct (DTO-Entity mapping)
- Lombok
- Springdoc-openapi (Swagger UI)
- JUnit 5, Mockito, AssertJ, MockMvc

## Start

```bash
mvn clean install
mvn spring-boot:run
```
- Swagger UI: `http://localhost:8092/property-view/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8092/property-view/api-docs`

## Packages

```
controller/   → REST controllers
service/      → Business logic
histogram/    → Strategy pattern for histogram calculation
repository/   → Spring Data JPA repositories + Specification for /search
entity/       → JPA entities and @Embeddable value objects (Address, Contacts, ArrivalTime)
dto/          → Request/response DTOs
mapper/       → MapStruct mappers (Entity-DTO)
exception/    → Custom exceptions + unified @RestControllerAdvice
config/       → Swagger/OpenAPI configuration
```

### Patterns

- **Specification Pattern** (`HotelSpecifications`)
- **Strategy Pattern** (`histogram/Strategy`) 
- **DTO + service layer**
