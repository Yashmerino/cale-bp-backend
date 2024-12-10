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
P.S: You can use [this website](https://jwtsecret.com/) to generate a JWT token.
2. Run `mvn exec:java` via terminal/console. Or just press `Build & Run` button in your IDE.
3. An Owner user with `owner` username and `YOUR_OWNER_PASSWORD` password which you can use to login to the app will be created by default.

## How to view existing endpoints?
 - After starting the app you can access `http://localhost:8081/swagger-ui/index.html` to view the existing endpoints.
![Image of Swagger endpoints](img/swagger.png)
