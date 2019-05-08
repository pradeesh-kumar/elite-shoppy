# Build stage
#
FROM maven:3.6-jdk-8 AS build
COPY eureka-server/src /home/app/src
COPY eureka-server/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

#
# Package Stage
#
FROM openjdk:8
COPY --from=build /home/app/target/eureka-server-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8111
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]


#
# Build stage
#
FROM maven:3.6-jdk-8 AS build
COPY . /tmp/eureka-server
RUN mvn -f /tmp/eureka-server/pom.xml clean package
