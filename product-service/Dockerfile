#
# Build stage
#
# Build eliteshoppy-common
FROM maven:3.6-jdk-8 AS build

MAINTAINER pradeeshbm67@gmail.com

COPY eliteshoppy-commons /tmp/eliteshoppy-commons
RUN mvn -f /tmp/eliteshoppy-commons/pom.xml clean install

# Build main project
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