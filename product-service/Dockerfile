#
# Build stage
#
# Build eliteshoppy-common
FROM maven:3.8-jdk-8 AS build

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
FROM gcr.io/eliteshoppy/access-key-image@sha256:c2c8dc7ff9a68ab609e9461f75f56d929a116767a299c33ac2e20ba77f133c9d
COPY --from=build /home/app/target/product-service-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
ENV GOOGLE_CLOUD_PROJECT=eliteshoppy
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]