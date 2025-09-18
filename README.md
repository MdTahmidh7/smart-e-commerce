# Smart E-Commerce

Smart E-Commerce is a Spring Boot-based e-commerce application in its initial stage. The project is fully dockerized for easy deployment and development.

---

## Features

### User
- Register a new account.
- Add products to the shopping cart.
- Place orders for products.

### Admin
- View statistical data regarding the e-commerce platform (e.g., total users, orders, and product stats).

---

## Technology Stack

- Backend: Spring Boot 3.5.5
- Java: OpenJDK 21
- Build Tool: Gradle 8.4.1
- Database: PostgreSQL 16 (Dockerized)
- Containerization: Docker, Docker Compose
- OS for Docker Images: Ubuntu Jammy + Alpine JRE for runtime

---

## Prerequisites

- Docker & Docker Compose installed.
- (Optional) Java 21 and Gradle 8.4.1 for local development.

---

## Running the Application with Docker

### Using Docker Compose (Recommended)

1. Clone the repository:

git clone https://github.com/MdTahmidh7/smart-e-commerce.git
cd smart-e-commerce

2. Build and start the application:

docker compose up --build

3. Access the application:

- Backend: http://localhost:8080
- Database: PostgreSQL exposed on port 5432

4. Stop the application:

docker compose down

### Quick Run with Single Docker Commands

- Build Docker image only:

docker build -t smart-ecommerce .

- Run the application container:

docker run -p 8080:8080 smart-ecommerce

- Rebuild and run after code changes:

docker build -t smart-ecommerce .
docker stop $(docker ps -q --filter ancestor=smart-ecommerce)
docker run -p 8080:8080 smart-ecommerce
