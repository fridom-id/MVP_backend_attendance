FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./build/libs/attendance-ms-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "attendance-ms-0.0.1-SNAPSHOT.jar"]