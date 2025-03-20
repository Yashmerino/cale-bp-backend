# VDSR Backend Project

## Requirements:
- Java 17
- PostgreSQL (I recommend using Docker for this)
- Maven

## How to run?
1. Modify `src/main/resources/application.properties`:
  ```yml
  # Datasource properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DB_NAME
  spring.datasource.username=YOUR_DB_USER
  spring.datasource.password=YOUR_DB_PASSWORD
  spring.datasource.driverClassName=org.postgresql.Driver

  # JWT
  jwt.secret = YOUR_JWT_SECRET

  # Details
  owner.email = YOUR_OWNER_EMAIL
  owner.password = YOUR_OWNER_PASSWORD
  ```
> You can use [this website](https://jwtsecret.com/) to generate a JWT token

2. Run `mvn exec:java` via terminal/console. Or just press `Build & Run` button in your IDE.
   
> An Owner user with `owner` username and `YOUR_OWNER_PASSWORD` password which you can use to login to the app will be created by default.

## How to view existing endpoints?
 - After starting the app you can access `http://localhost:8081/swagger-ui/index.html` to view the existing endpoints.
![Image of Swagger endpoints](img/swagger.png)

## How to run using Docker Compose?
1. Create a `docker-compose.yml` file and modify the properties.
```yml
services:
  # Database service
  vdsr-postgres:
    image: postgres:latest
    container_name: vdsr-postgres
    networks:
      - vdsr-network
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: YOUR_POSTGRES_USER
      POSTGRES_PASSWORD: YOUR_POSTGRES_PASSWORD
      POSTGRES_DB: vdsr
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U YOUR_POSTGRES_USER"]
      interval: 10s
      retries: 5
      start_period: 60s

  # Server service
  vdsr-backend:
    image: yashmerino/vdsr-backend:latest
    container_name: vdsr-backend
    networks:
      - vdsr-network
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://vdsr-postgres:5432/vdsr
      SPRING_DATASOURCE_USERNAME: YOUR_POSTGRES_USERNAME
      SPRING_DATASOURCE_PASSWORD: YOUR_POSTGRES_PASSWORD
      owner.email: YOUR_OWNER_EMAIL
      owner.password: YOUR_OWNER_PASSWORD
      jwt.secret: YOUR_JWT_SECRET
    depends_on:
      vdsr-postgres:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "--fail", "http://localhost:8081/actuator/health"]
      interval: 10s
      retries: 5
      start_period: 60s

networks:
  vdsr-network:
    driver: bridge
```
2. Run the Docker Compose using `docker-compose up -d`.
