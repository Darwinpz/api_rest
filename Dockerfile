FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/app/target/api_rest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]