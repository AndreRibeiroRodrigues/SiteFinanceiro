FROM maven:3.9-eclipse-temurin-25-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -ntp clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=build /app/target/finand-0.0.1-SNAPSHOT.jar app.jar

# Configure DB_HOST, DB_PORT, DB_NAME, DB_USER and DB_PASSWORD at runtime.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
