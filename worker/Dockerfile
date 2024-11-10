FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
ENV SERVER_PORT=8082
ENTRYPOINT ["java", "-jar", "app.jar"]