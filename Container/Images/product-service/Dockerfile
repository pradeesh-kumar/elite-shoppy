FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ..\..\..\product-service\target\product-service-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]