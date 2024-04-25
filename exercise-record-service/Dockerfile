FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} ExerciseRecordService.jar
ENTRYPOINT ["java", "-jar", "ExerciseRecordService.jar", "--spring.profiles.active=prod"]