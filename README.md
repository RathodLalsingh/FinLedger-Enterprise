# 💰 FinLedger Enterprise

> A production-ready **Personal Finance Management Backend** built with **Spring Boot**, featuring secure authentication, Redis caching, report generation, cloud storage, Docker deployment, and enterprise-level architecture.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![JWT](https://img.shields.io/badge/JWT-Authentication-success)
![Docker](https://img.shields.io/badge/Docker-Container-blue)
![Render](https://img.shields.io/badge/Render-Deployed-success)

---

# 🚀 Features

- 🔐 JWT Authentication & Refresh Tokens
- 👤 User Registration & Login
- 💰 Income Management
- 💸 Expense Management
- 🏷 Category Management
- 📊 Dashboard Analytics
- 🔍 Transaction Filtering & Search
- 🔔 Notification Management
- ☁ Cloudinary Profile Image Upload
- 📄 Excel Report Export (Apache POI)
- 📧 Email Notifications & Daily Reminders
- ⚡ Redis Caching
- 🚀 Database Indexing & Query Optimization
- 🛡 Global Exception Handling
- ✅ Request Validation
- 📑 Swagger API Documentation
- 🧪 Unit & Integration Testing

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Database

- PostgreSQL

## Cache

- Redis

## Authentication

- JWT
- Refresh Tokens

## Cloud

- Cloudinary

## Reports

- Apache POI

## Email

- Java Mail Sender

## Testing

- JUnit 5
- Mockito

---

# 🏗 Architecture

```text
                  Client
                     │
                     ▼
          Spring Security (JWT)
                     │
                     ▼
              REST Controllers
                     │
                     ▼
               Service Layer
                     │
          ┌──────────┴──────────┐
          ▼                     ▼
     PostgreSQL            Redis Cache
          │
          ▼
     Cloudinary
          │
          ▼
     Email Service
```

---

# 📂 Project Structure

```text
src
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
├── util
└── resources
```

---

# ⭐ Enterprise Concepts

- Layered Architecture
- RESTful API Design
- DTO Pattern
- Spring Security
- JWT Authentication
- Refresh Token Authentication
- Redis Caching
- Query Optimization
- Database Indexing
- Global Exception Handling
- Bean Validation
- Cloud File Storage
- Email Integration
- Excel Report Generation
- Unit Testing
- Integration Testing

---

# ⚙ Environment Variables

Create an **application.properties** or configure these variables on Render.

```properties
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

JWT_SECRET=
JWT_EXPIRATION=

REDIS_HOST=
REDIS_PORT=

SPRING_MAIL_USERNAME=
SPRING_MAIL_PASSWORD=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=
```

---

# ▶ Running Locally

## Clone Repository

```bash
git clone https://github.com/yourusername/FinLedger-Enterprise.git

cd FinLedger-Enterprise
```

## Build Project

```bash
mvn clean install
```

## Run Application

```bash
mvn spring-boot:run
```

OR

```bash
java -jar target/finledger-enterprise.jar
```

---

# 🐳 Docker Deployment

## Dockerfile

```dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
```

---

## Build Docker Image

```bash
docker build -t finledger-enterprise .
```

---

## Run Docker Container

```bash
docker run -d \
-p 8080:8080 \
--name finledger \
finledger-enterprise
```

Application will start on

```
http://localhost:8080
```

---

# ☁ Render Deployment

## Step 1

Push the project to GitHub.

---

## Step 2

Create a PostgreSQL Database
(Render PostgreSQL or Neon PostgreSQL)

---

## Step 3

Create a New Web Service on Render

Choose

```
Build Type : Docker
```

---

## Step 4

Select your GitHub Repository.

---

## Step 5

Render automatically detects

```
Dockerfile
```

---

## Step 6

Configure Environment Variables

```
DATABASE_URL=...

DATABASE_USERNAME=...

DATABASE_PASSWORD=...

JWT_SECRET=...

JWT_EXPIRATION=86400000

REDIS_HOST=...

REDIS_PORT=6379

SPRING_MAIL_USERNAME=...

SPRING_MAIL_PASSWORD=...

CLOUDINARY_CLOUD_NAME=...

CLOUDINARY_API_KEY=...

CLOUDINARY_API_SECRET=...
```

---

## Step 7

Click

```
Deploy
```

Render will

```
Build Docker Image
        │
        ▼
Create Container
        │
        ▼
Start Spring Boot Application
        │
        ▼
Application Live
```

---

# 📖 API Documentation

## Swagger UI (Local)

```
http://localhost:8080/swagger-ui/index.html
```

## Swagger UI (Production)

```
https://your-render-url.onrender.com/swagger-ui/index.html
```

---

# 📌 Sample APIs

| Method | Endpoint |
|----------|--------------------------|
| POST | /api/auth/register |
| POST | /api/auth/login |
| GET | /api/dashboard |
| POST | /api/income |
| POST | /api/expense |
| GET | /api/category |
| GET | /api/report/excel |

---

# ⚡ Performance Optimizations

- Redis Caching
- Database Indexing
- Query Optimization
- Pagination
- DTO Mapping
- Transaction Management
- Lazy Loading
- Exception Handling

---

# 🔒 Security

- JWT Authentication
- Refresh Tokens
- BCrypt Password Encryption
- Stateless Authentication
- Spring Security
- Role-based Authorization
- Secure REST APIs

---

# 🧪 Testing

Run all tests

```bash
mvn test
```

Frameworks

- JUnit 5
- Mockito

---

# 📈 Future Improvements

- GitHub Actions CI/CD
- Docker Compose
- Prometheus Monitoring
- Grafana Dashboard
- Kafka Integration
- Kubernetes Deployment
- AWS S3 Storage
- Microservices Architecture

---

# 👨‍💻 Author

## Rathod Lalsingh

**Java Backend Developer**

- Java
- Spring Boot
- Spring Security
- PostgreSQL
- Redis
- Docker
- JWT Authentication
- REST APIs

---
