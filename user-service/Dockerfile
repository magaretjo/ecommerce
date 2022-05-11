#FROM openjdk:17-ea-11-jdk-slim
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/user-service-1.0.jar UserServer.jar
ENTRYPOINT ["java", "-jar", "UserServer.jar"]