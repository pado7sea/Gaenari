FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} FcmService.jar
ENTRYPOINT ["java", "-jar", "FcmService.jar", "--spring.profiles.active=prod"]