FROM openjdk:8
COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#
# Build stage
#
FROM maven:3.6-jdk-8 AS build
COPY auth-service/src /home/app/src
COPY auth-service/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package Stage
#
FROM openjdk:8
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]