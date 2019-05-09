MAINTAINER pradeeshbm67@gmail.com

#
# Build stage
#
# Build eliteshoppy-common
FROM maven:3.6-jdk-8 AS build
COPY eliteshoppy-commons /tmp/eliteshoppy-commons
RUN mvn -f /tmp/eliteshoppy-commons/pom.xml clean install

# Build main project
COPY eliteshoppy-web/src /home/app/src
COPY eliteshoppy-web/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package Stage
#
FROM openjdk:8
COPY --from=build /home/app/target/eliteshoppy-web-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]