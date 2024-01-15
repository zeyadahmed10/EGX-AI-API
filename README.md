# EGX-AI API
EGX-AI is a user-friendly RESTful microservices API developed using Java Spring Boot 3. It provides real-time updates on Egyptian stock market news, prices, and comprehensive OHCLV (Open, High, Close, Low, Volume) statistics. Users have the ability to subscribe to email notifications for price changes and stay informed about news updates related to their selected equities. Additionally, the API includes a recommendation system that suggests news articles based on users' reading history.

The project follows a modern event driven microservice architecture, ensuring scalability and flexibility. It incorporates a secure authentication system powered by OAuth2 and OIDC with Keycloak, guaranteeing the confidentiality and integrity of user data. The primary database used is Postgres, while TimescaleDB efficiently handles the storage of time-series stock market data.

To simplify the deployment and management processes, the services are containerized using Docker. This approach allows for easy setup and ensures consistent execution across different environments.

## Table of contents
- [Features](#features)
- [Installation](#installation)
- [usage](#usage)
- [Project Architecture](#project-architecture)
- [Tools and Technologies](#tools-and-technologies)
## Features
- Real-time updates on Egyptian stock market news, prices, and comprehensive OHCLV statistics (Open, High, Close, Low, Volume).
- Email notification subscriptions for price changes and news updates related to selected equities.
- Recommendation system suggesting news articles based on users' reading history.
- Refactored from request-driven to event-driven microservices architecture with Kafka, resulting in a 28% increase in performance and robustness.
- Secure authentication system using Keycloak.
- Global exception handling and logging
- Containerization using Docker for simplified deployment and management.
- comprehensive suite of tests(unit, Integration) to ensure code quality and reliability with 85% code coverage.
- Database migration with flyway.

## Installation
To run the EGX-AI API locally, please follow these steps:

1. Install Docker on your machine. Refer to the official Docker documentation for installation instructions: [Docker Installation Guide](https://docs.docker.com/get-docker/).

2. Clone the repository:

   ```bash
   git clone https://github.com/your-username/egx-ai-api.git
   
3. Navigate to the project directory:
    ```bash 
    cd egx-ai-api
4. Run Docker Compose to start the services:
    ```bash
    docker compose up -d
   This command will start the required containers and set up the necessary dependencies. The API will be accessible at `http://localhost:8083`.

## Usage
The EGX-AI API provides a Swagger documentation for each service. You can access the Swagger UI by opening the following URL in your browser:

> http://localhost:8080/swagger-ui.html (change the port number to the desired service documentation can be found inn docker-compose.yml)

The Swagger documentation provides detailed information about each endpoint and allows you to interact with the API directly.

## Project Architecture
- **Scraping Service**: Scrape News and Stock prices for Egyptian equities listed in stock market periodically.
- **Stock Service**: Manging stock prices, updates stock prices and provide OHCLV statistics for historical data.
- **News Service**: Manging news for equities and capture user readings.
- **notifications**: Subscribe users to newsletter for chosen equity and stock prices changes.
- **recommendation services**: Recommend news to users based on their readings' history and stores news embedding.
- **Auth Service:** responsible for user registration, authentication and authorization using Keycloak.
- **Api Gateway:** single entry point for all incoming requests, and routes requests to the appropriate microservice.
- **Eureka Server:** naming server for registering microservices and load balancing.

![micro](https://github.com/zeyadahmed10/EGX-AI-API/blob/main/EGX-AI-archi.png)

## Tools and Technologies
- **Java 17**
- **Spring Boot** - version 3.1.2
- **Spring Cloud** - version 2202.0.4
- **KeyCloak** - version 22
- **Spring Web**
- **OAuth Resource Server**
- **Open Feign**
- **Eureka Server**
- **Spring Gateway**
- **Spring Data Jpa**
- **Hibernate Validator**
- **HuggingFace & DJL 0.25**
- **Lombok**
- **JUnit & Mockito**
- **flyway**
- **Maven**
