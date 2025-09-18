# ==============================
# Build Stage
# ==============================
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Make wrapper executable
RUN chmod +x gradlew

# Build the fat JAR with full logs for debugging
RUN ./gradlew clean bootJar --stacktrace --info --no-daemon

# ==============================
# Run Stage
# ==============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
