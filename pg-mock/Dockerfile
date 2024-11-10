FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
ENV SERVER_PORT=8081
ENTRYPOINT ["java", "-jar", "app.jar"]