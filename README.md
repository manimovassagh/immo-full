# Property Rental Application

A Spring Boot application for property rental management with user registration, property listing, and messaging functionality.

## Features

- User registration and authentication
- Property listing and management
- Property search and filtering
- Messaging system between users
- Responsive design with Bootstrap 5
- Thymeleaf templates for server-side rendering

## Technology Stack

- Java 21
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Bootstrap 5
- Maven

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd immo-full
```

2. Create a PostgreSQL database:
```sql
CREATE DATABASE immo_db;
```

3. Update the database configuration in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/immo_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

5. Access the application at `http://localhost:8080`

## Project Structure

```
src/main/java/com/immo/
├── config/          # Configuration classes
├── controller/      # MVC Controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA Entities
├── repository/     # JPA Repositories
├── service/        # Service interfaces
└── service/impl/   # Service implementations

src/main/resources/
├── static/         # Static resources (CSS, JS)
├── templates/      # Thymeleaf templates
└── application.properties
```

## Security

The application uses Spring Security for authentication and authorization. By default, the following endpoints are accessible without authentication:

- `/` - Home page
- `/properties` - Property listing
- `/properties/{id}` - Property details
- `/register` - User registration
- `/login` - Login page

All other endpoints require authentication.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 