# Patient Management System

A microservices-based patient management system built with Spring Boot.

## Services

- **api-gateway** – single entry point, routes to all services (port 4004)
- **auth-service** – login and JWT validation (port 4005)
- **patient-service** – manages patient records (port 4000)
- **billing-service** – creates billing accounts, called via gRPC (port 4001, gRPC 9002)
- **analytics-service** – consumes patient events from Kafka

## Tech Stack

Java 17, Spring Boot, PostgreSQL, gRPC, Kafka, Docker

## Running Locally

```bash
git clone <your-repo-url>
cd patient-management1
docker compose up -d
```

## API Endpoints

**Auth** (`/auth/**`)
- `POST /login` – get a JWT
- `GET /validate` – validate a token

**Patients** (`/api/patients/**`)
- `GET /patients`
- `POST /patients`


***Patient Service***

***Environment Variables***
JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005;
SPRING_DATASOURCE_PASSWORD=password;
SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db;
SPRING_DATASOURCE_USERNAME=admin_user;
SPRING_JPA_HIBERNATE_DDL_AUTO=update;
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092;
SPRING_SQL_INIT_MODE=always

Billing Service
Environment Variables 
BILLING_SERVICE_ADDRESS=billing-service;
BILLING_SERVICE_GRPC_PORT=9005;
JAVA_TOOL_OPTIONS=-agentlib:jdwp\=transport\=dt_socket,server\=y,suspend\=n,address\=*:5005;
SPRING_DATASOURCE_PASSWORD=password;
SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db;
SPRING_DATASOURCE_USERNAME=admin_user;
SPRING_JPA_HIBERNATE_DDL_AUTO=update;
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092;
SPRING_SQL_INIT_MODE=always

Kafka Container
Copy/paste this line into the environment variables when running the container in intellij

KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,
EXTERNAL://localhost:9094;
KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER;
KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka:9093;
KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT;
KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094;
KAFKA_CFG_NODE_ID=0;KAFKA_CFG_PROCESS_ROLES=controller,broker

Analytic Service
Environment Vars
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092

Auth Service
Environment Variables
SPRING_DATASOURCE_PASSWORD=password
SPRING_DATASOURCE_URL=jdbc:postgresql://auth-service-db:5432/db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=always


Data.sql
-- Ensure the 'users' table exists
CREATE TABLE IF NOT EXISTS "users" (
id UUID PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(50) NOT NULL
);

-- Insert the user if no existing user with the same id or email exists
INSERT INTO "users" (id, email, password, role)
SELECT '223e4567-e89b-12d3-a456-426614174006', 'testuser@test.com',
'$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'ADMIN'
WHERE NOT EXISTS (
SELECT 1
FROM "users"
WHERE id = '223e4567-e89b-12d3-a456-426614174006'
OR email = 'testuser@test.com'
);


Auth Service DB
Environment Variables
POSTGRES_DB=db;POSTGRES_PASSWORD=password;POSTGRES_USER=admin_user