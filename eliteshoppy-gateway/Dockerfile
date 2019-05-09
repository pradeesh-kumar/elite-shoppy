#
# Build stage
#
FROM maven:3.6-jdk-8 AS build

MAINTAINER pradeeshbm67@gmail.com

COPY eliteshoppy-gateway/src /home/app/src
COPY eliteshoppy-gateway/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package Stage
#
FROM openjdk:8
COPY --from=build /home/app/target/eliteshoppy-gateway-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8111
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]