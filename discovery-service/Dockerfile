FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar", "--spring.profiles.active=prod"]