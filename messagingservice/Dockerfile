FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=messagingservice/target/messagingservice-1.0-RELEASE.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]