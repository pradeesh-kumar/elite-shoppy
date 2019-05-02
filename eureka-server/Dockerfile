FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ..\..\..\eureka-server\target\eureka-server-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]