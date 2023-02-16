FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY common-lib/ ./common-lib/
COPY gradle/ ./gradle/
COPY gradlew ./
COPY build.gradle ./
COPY settings.gradle ./
COPY src/ ./src/

RUN chmod a+x gradlew

RUN ./gradlew clean build -x test
RUN mv ./build/libs/attendance-ms-*-SNAPSHOT.jar ./attendance-ms.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "attendance-ms.jar"]
