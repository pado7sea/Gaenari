FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} MemberService.jar
ENTRYPOINT ["java", "-jar", "MemberService.jar", "--spring.profiles.active=prod"]