# ⚔️ CodeClash — 1v1 LAN Competitive Coding Arena

CodeClash is a real-time 1v1 competitive coding platform running over a local network (LAN / Wi-Fi / Hotspot). Two players join a private room, solve an algorithmic challenge under a countdown timer, compile and run Java code, and compete for victory and Elo rating progression.

---

## 🚀 Tech Stack

- **Backend**: Spring Boot 3.3.4 (Java 21/25), Spring Data JPA, Spring Security Crypto (BCrypt)
- **Database**: MySQL 8.0 (with Hibernate auto-schema generation)
- **Frontend**: Responsive HTML5, Modern CSS, Vanilla JavaScript (ES6 modules)
- **Code Execution**: Built-in Sandboxed Local Java Compiler & Runner (with Judge0 API support)

---

## 🛠️ Setup & Running Locally

### 1. Prerequisites
- **JDK 21+** (OpenJDK / Temurin)
- **MySQL 8.0** running on port `3306`

### 2. Configure Database Credentials
Edit `src/main/resources/application.properties` with your MySQL password:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/codeclash_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3. Run the Backend Server
On the host laptop:
```bash
./mvnw spring-boot:run
# or if Maven is installed:
mvn spring-boot:run
```

### 4. Connect Over Local LAN
1. Find your host laptop's local IPv4 address (e.g. `192.168.1.45` via `ipconfig` on Windows).
2. Both players open the browser:
   - Host: `http://localhost:8080`
   - Opponent on same Wi-Fi: `http://192.168.1.45:8080`

---

## 📂 Project Structure

```
CodeClash/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    └── main/
        ├── java/com/codeclash/
        │   ├── CodeClashApplication.java
        │   ├── config/             # CORS & Security configs
        │   ├── controller/         # REST Controllers (Auth, Rooms, Matches, Submissions)
        │   ├── dto/                # Request / Response payloads
        │   ├── model/              # JPA Entities (User, Problem, TestCase, Room, Match, etc.)
        │   ├── repository/         # Spring Data JPA Repositories
        │   └── service/            # Business logic & Code Execution Engines
        └── resources/
            ├── application.properties
            └── static/             # Frontend HTML/CSS/JS assets
```

---

## 👥 Team Collaboration Guide
1. **Clone the repo**: `git clone https://github.com/iamkrish-0708/CodeClash.git`
2. **Create a feature branch**: `git checkout -b feature/your-feature-name`
3. **Commit & Push**: `git push origin feature/your-feature-name`
4. **Open a Pull Request** on GitHub for code review.
