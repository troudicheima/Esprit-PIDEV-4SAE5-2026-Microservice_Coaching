# Coaching Service – Coaching Management Application

## Overview

This project was developed as part of the PIDEV – 4rd Year Engineering Program at **Esprit School of Engineering** (Academic Year 2025–2026).

It consists of a full-stack coaching management service that allows coaches and students to schedule coaching sessions, manage reservations, and communicate in real-time through an integrated chat system.

## Features

- **Session Management**: Create, update, delete, and view coaching sessions (Séances)
- **Reservation System**: Students can reserve seats for coaching sessions
- **Real-time Chat**: WebSocket-based instant messaging for communication
- **Microservices Architecture**: Uses Eureka for service discovery and Feign for inter-service communication
- **RESTful API**: Complete CRUD operations for sessions and reservations
- **MySQL Database**: Persistent storage with JPA/Hibernate

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- Spring WebSocket
- Spring Cloud (Eureka, OpenFeign)
- MySQL
- Lombok

### Frontend
- Angular (provided in `src/main/resources/static/chat/`)
- TypeScript

## Architecture

```
Coaching-service/
├── src/main/java/tn/esprit/coachingservice/
│   ├── CoachingServiceApplication.java    # Main application entry
│   ├── Config/
│   │   ├── WebSocketConfig.java           # WebSocket configuration
│   │   └── ServiceBClient.java            # Feign client configuration
│   ├── Controller/
│   │   ├── SeanceController.java          # Session REST endpoints
│   │   ├── ReservationController.java     # Reservation REST endpoints
│   │   └── ChatController.java            # WebSocket chat handler
│   ├── Entity/
│   │   ├── Seance.java                    # Session entity
│   │   └── Reservation.java               # Reservation entity
│   ├── Service/
│   │   ├── SeanceService.java             # Session business logic
│   │   └── ReservationService.java        # Reservation business logic
│   ├── Repository/
│   │   ├── SeanceRepository.java          # Session data access
│   │   └── ReservationRepository.java     # Reservation data access
│   ├── Model/
│   │   └── ChatMessage.java               # Chat message model
│   └── Dto/
│       └── UserDto.java                   # User data transfer object
├── src/main/resources/
│   ├── application.properties             # Application configuration
│   └── static/chat/                       # Angular frontend files
└── pom.xml                                # Maven dependencies
```

### API Base URL
```
http://localhost:5057/Coaching-service/api
```

### Key Endpoints

**Sessions (Séances):**
- `GET /api/seances` - Get all sessions
- `POST /api/seances` - Create a session
- `GET /api/seances/{id}` - Get session by ID
- `PUT /api/seances/{id}` - Update a session
- `DELETE /api/seances/{id}` - Delete a session

**Reservations:**
- `GET /api/reservations` - Get all reservations
- `POST /api/seances/{seanceId}/reservations` - Create a reservation
- `GET /api/seances/{seanceId}/reservations` - Get reservations by session
- `PUT /api/reservations/{id}` - Update a reservation
- `DELETE /api/reservations/{id}` - Delete a reservation

**WebSocket Chat:**
- Connect to: `/ws`
- Subscribe to: `/topic/public`
- Send messages to: `/app/chat`

## Contributors

- Developed by PIDEV 3rd Year Engineering Students
- **Esprit School of Engineering**

## Academic Context

Developed at **Esprit School of Engineering – Tunisia**

PIDEV – 3A | 2025–2026

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Node.js and Angular CLI (for frontend)

### Configuration

The application is pre-configured with the following settings in `application.properties`:

```properties
spring.application.name=Coaching-service
server.port=5057
spring.datasource.url=jdbc:mysql://localhost:3306/Coaching-service
spring.datasource.username=root
spring.datasource.password=
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

### Running the Application

1. **Clone the repository**
2. **Configure MySQL**: Ensure MySQL is running and accessible
3. **Build the project**:
   ```bash
   ./mvnw clean install
   ```
4. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```
5. **Access the API**: The service will be available at `http://localhost:5057/Coaching-service`

### Database

The database `Coaching-service` will be automatically created if it doesn't exist. The schema is auto-generated using Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

## Acknowledgments

- **Esprit School of Engineering** for providing the academic framework
- Spring Boot and Spring Cloud communities for the excellent documentation
- All contributors and team members

---


