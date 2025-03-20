# Use Maven image to build the project
FROM maven:3.8.1-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Download dependencies and build the project, skipping tests
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

# Use a lightweight OpenJDK image for running the application
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the Maven build stage
COPY --from=build /app/target/*.jar vdsr-backend.jar

# Expose application port
EXPOSE 8081

# Install curl (optional, remove if not needed)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Command to run the application
CMD ["java", "-jar", "vdsr-backend.jar"]
