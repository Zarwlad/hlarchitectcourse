FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/hlarchitectcourse-1.0.0.jar
COPY ${JAR_FILE} hlarchitectcourse-1.0.0.jar
ENTRYPOINT ["java","-jar","/hlarchitectcourse-1.0.0.jar"]
