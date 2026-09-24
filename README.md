# ⚔️ CodeClash — 1v1 Real-Time Competitive Coding Arena

CodeClash is a full-stack, real-time 1v1 competitive coding platform. Two duelists enter a match room, solve algorithmic challenges in an integrated code editor under a countdown timer, execute and test Java code against test suites, and battle head-to-head to climb the global Elo leaderboard.

---

## ✨ Features

- **⚡ 1v1 Matchmaking & Custom Rooms**: Create private clash rooms with unique 6-character room codes or join existing rooms with live radar scanning matchmaking.
- **💻 Interactive Code Editor**: Monaco-powered in-browser code editor with Java syntax highlighting, starter code templates, and sample test case execution.
- **🔒 Authentication & Google Sign-In**: Secure account creation with strict validation rules, BCrypt password hashing, and one-tap Google Sign-In with automatic Duelist Handle onboarding.
- **🏆 Elo Rating Engine & Global Leaderboard**: Standard K-factor Elo rating system that updates player standings and podium rankings after every victory, draw, or defeat.
- **📚 Challenge Vault**: Curated library of algorithmic challenges categorized by difficulty (Easy, Medium, Hard) with custom memory and time limits.
- **☁️ Zero-Config & Cloud Deployment Ready**: Runs seamlessly out of the box with embedded zero-config H2 storage or connects to production MySQL/cloud databases on platforms like Render.

---

## 🚀 Tech Stack

- **Backend**: Spring Boot 3.3.4 (Java 21/25), Spring Data JPA, Spring Security Crypto (BCrypt)
- **Authentication**: Google API Client (OAuth 2.0 / Google Identity Services) + Session-based auth
- **Database**: Embedded H2 Database (zero-config default) with full MySQL 8.0 compatibility
- **Frontend**: Responsive HTML5, Modern CSS Design System (Near-black + Coral accent), Vanilla JavaScript (ES6+), Lucide Icons
- **Code Execution**: Built-in Sandboxed Local Java Compiler & Runner (with Judge0 API support)

---

## 🛠️ Setup & Running Locally

### 1. Prerequisites
- **JDK 21+** (OpenJDK / Temurin / Oracle)
- **Maven 3.8+** (or bundled wrapper/tools)

### 2. Clone & Run
```bash
git clone https://github.com/iamkrish-0708/CodeClash.git
cd CodeClash

# Run via Maven
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### 3. (Optional) Database Configuration
By default, CodeClash runs with embedded H2 (no extra installation needed). To use MySQL or an external database, configure environment variables or update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/codeclash_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## ☁️ Deployment (e.g. Render)

1. Connect your GitHub repository to [Render](https://render.com) as a **Web Service**.
2. **Build Command**: `mvn clean package -DskipTests`
3. **Start Command**: `java -jar target/codeclash-0.0.1-SNAPSHOT.jar`
4. (Optional) Set environment variables:
   - `GOOGLE_CLIENT_ID`: Your Google OAuth Client ID
   - `DATABASE_URL`: (Optional) External database connection string

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
        │   ├── config/             # Security, JPA & Data Initializers
        │   ├── controller/         # REST Endpoints (Auth, Rooms, Matches, Submissions, Leaderboard)
        │   ├── dto/                # Request & Response Data Transfer Objects
        │   ├── model/              # JPA Entities (User, Problem, TestCase, Room, Match, Submission)
        │   ├── repository/         # Spring Data JPA Repositories
        │   └── service/            # Business Logic, Elo Calculator & Code Execution Engine
        └── resources/
            ├── application.properties
            └── static/             # Frontend Client
                ├── css/style.css   # Near-Black & Coral Design System
                ├── js/api.js       # Unified REST API Client
                ├── index.html      # Landing Page & Feature Showcase
                ├── dashboard.html  # Duelist Dashboard & Challenge Pool
                ├── arena.html      # 1v1 Live Match Coding Arena
                ├── lobby.html      # Room Lobby & Matchmaking Radar
                ├── leaderboard.html# Global Podium & Elo Rankings
                ├── login.html      # Login & Google Auth
                └── register.html   # Registration with Requirement Checklist
```

---

## 👥 Team Collaboration Guide
1. **Clone the repository**: `git clone https://github.com/iamkrish-0708/CodeClash.git`
2. **Create a feature branch**: `git checkout -b feature/your-feature-name`
3. **Commit your changes**: `git commit -m "feat: describe your change"`
4. **Push to GitHub**: `git push origin feature/your-feature-name`
5. **Open a Pull Request** on GitHub for review.
