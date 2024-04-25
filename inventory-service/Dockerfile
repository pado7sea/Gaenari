FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} InventoryService.jar
ENTRYPOINT ["java", "-jar", "InventoryService.jar", "--spring.profiles.active=prod"]