# Product Management System - Backend

A Spring Boot REST API for managing products, categories, and user authentication, backed by Microsoft SQL Server.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Microsoft SQL Server
- Maven
- JWT Authentication

## Features

- User registration and login
- JWT-based authentication
- CRUD operations for products
- CRUD operations for categories
- Product search
- Request validation and exception handling
- SQL Server database integration

## Getting Started

### Prerequisites
- Java JDK
- Maven
- Microsoft SQL Server

### Configuration

Configure your database connection and other required settings in `src/main/resources/application.properties`.

Ensure the database exists and the credentials are correct.

### Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Alternatively, run the main Spring Boot application class from your IDE.

The API will start on the configured server port.

## API

The backend exposes REST endpoints for authentication, products, and categories. Use the configured API base URL when connecting the frontend.
