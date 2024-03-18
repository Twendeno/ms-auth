# BUILD
FROM maven:3-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY ms-auth/pom.xml .
COPY ms-auth/src ./src
RUN mvn clean package -Dmaven.test.skip=true

# DEPLOY
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
ARG SERVER_PORT=8080
ENV SERVER_PORT=${SERVER_PORT}
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]