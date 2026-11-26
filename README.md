# 💳 Digital Banking Onboarding & Identity Verification Service

[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

A backend microservice for banking customer onboarding and automated two-factor identity verification (OTP). Built with **Java 21**, **Spring Boot 3**, and **Spring Data JPA**, the service handles secure phone verification, time-restricted OTP lifecycles, and verification state persistence against a PostgreSQL database.

---

## 🌟 Key Features

- 🔐 **One-Time Password (OTP) Generation**
  - Cryptographically secure randomized 6-digit OTP dispatch.
  - Configurable expiration window (default 5 minutes) to protect against replay attacks.

- 📲 **Onboarding Identity Verification State Machine**
  - Lifecycle tracking: `PENDING` ➔ `VERIFIED` / `EXPIRED`.
  - Audited timestamps (`createdAt`, `expiresAt`) linked to verified phone numbers.

- 🗄️ **Persistent Financial Audit Trail**
  - Managed via Spring Data JPA with PostgreSQL integration.
  - Auto-updating schema DDL with comprehensive SQL logging for transaction visibility.

- ⚡ **RESTful Architecture**
  - Clean DTO layer with Spring Validation constraints.
  - Layered architecture separating Controller, Service, and Repository concerns.

---

## 🏛️ Project Structure

```
Fintech-Projects/
├── pom.xml                                       # Maven build & dependencies (Java 21, Spring Boot 3)
└── src/
    ├── main/
    │   ├── java/com/coopbank/onboarding/
    │   │   ├── OnboardingApplication.java        # Spring Boot main entry point
    │   │   ├── config/                           # Initial configuration & DataLoader
    │   │   └── verification/
    │   │       ├── controller/                   # REST API Controllers (VerificationController)
    │   │       ├── dto/                          # Request & Response DTO records (RequestOtpDto)
    │   │       ├── entity/                       # JPA entities (VerificationRequest, VerificationStatus)
    │   │       ├── repository/                   # Spring Data JPA interfaces
    │   │       └── service/                      # Business logic & OTP generation (VerificationService)
    │   └── resources/
    │       └── application.properties            # Datasource & JPA configuration
    └── test/                                     # Unit & integration test suites
```

---

## 🚀 API Endpoints

### Request OTP
```http
POST /api/verifications/request-otp
Content-Type: application/json

{
  "phoneNumber": "+251912345678"
}
```

**Response (`200 OK`):**
```text
"OTP Generated Successfully"
```

---

## 🛠️ Getting Started

### Prerequisites
- [JDK 21](https://adoptium.net/) or higher
- [Maven 3.8+](https://maven.apache.org/) (or use the included `./mvnw`)
- [PostgreSQL](https://www.postgresql.org/) instance running locally or via Docker

### Run Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Nahom2231/Fintech-Projects.git
   cd Fintech-Projects
   ```

2. **Configure Database Connection:**
   In `src/main/resources/application.properties`, adjust your PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/onboarding_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   ```

3. **Build and Run:**
   ```bash
   ./mvnw clean spring-boot:run
   ```
   The service will start on port `8080`.

---

## 👨‍💻 Author

**Nahom** — Full-Stack Software Developer  
- GitHub: [@Nahom2231](https://github.com/Nahom2231)  
- Email: [nahom0462@gmail.com](mailto:nahom0462@gmail.com)
