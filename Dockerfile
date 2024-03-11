# Build stage
FROM gradle:8.5-jdk-alpine AS build
COPY . .
RUN gradle build || exit 1

# Runtime stage
FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=build /home/gradle/build/libs/homebanking-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]