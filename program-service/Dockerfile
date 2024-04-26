FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} ProgramService.jar
ENTRYPOINT ["java", "-jar", "ProgramService.jar", "--spring.profiles.active=prod"]