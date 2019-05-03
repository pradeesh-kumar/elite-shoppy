FROM openjdk:8
COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#
# Build stage
#
FROM maven:3.6-jdk-8 AS build
COPY product-service/src /home/app/src
COPY product-service/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package Stage
#
FROM openjdk:8
COPY --from=build /home/app/target/product-service-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]