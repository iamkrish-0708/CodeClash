# Build stage with Maven and OpenJDK 21
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy project definition and source files
COPY pom.xml .
COPY src/ ./src/

# Install Maven in container and package the application
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Run stage with OpenJDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy compiled jar from build stage
COPY --from=build /app/target/codeclash-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
