FROM gradle:8.5-jdk-alpine

COPY . .

EXPOSE 8080

RUN gradle build

ENTRYPOINT ["JAVA", "-jar", "build/libs/homebanking-0.0.1-SNAPSHOT.jar"]
