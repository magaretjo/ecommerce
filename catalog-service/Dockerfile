FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/catalog-service-1.0.jar CatalogServer.jar
ENTRYPOINT ["java", "-jar", "CatalogServer.jar"]