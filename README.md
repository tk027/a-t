# Customer Management Service

This is a Spring Boot application that provides a RESTful API for managing customers.

## Features

- Create a new customer
- Retrieve customers by their last name

## Technologies

- Java 17
- Spring Boot 3.2
- Gradle
- H2 Database

## Setup

To run this project, you need to have Java 17 installed on your machine.

Use [TestDemoApplication](src/test/java/com/customer/demo/TestDemoApplication.java) to run the application with some bootstrapped users for testing purposes.

Alternatively, use ````./gradlew bootRun```` to run the application using main class [DemoApplication](src/main/java/com/customer/demo/DemoApplication.java).

User credentials can be found in [SecurityConfig](src/main/java/com/customer/demo/config/SecurityConfig.java). Use these credentials in basic authorization.

## API Endpoints

| Type | Url                            | Description                           | Sample Request                                                         |
|------|--------------------------------|---------------------------------------|------------------------------------------------------------------------|
| POST | /customers                     | Create a new customer                 | {"firstName": "Test", "lastName": "1211", "dateOfBirth": "1901-10-17"} |
| GET  | /customers?lastName={lastName} | Retrieve customers by their last name | /customers?lastName=Bootstrapped                                       |