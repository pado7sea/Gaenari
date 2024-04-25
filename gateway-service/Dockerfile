FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} GatewayService.jar
ENTRYPOINT ["java", "-jar", "GatewayService.jar", "--spring.profiles.active=prod"]