FROM eclipse-temurin:20-alpine
ARG UBIK_SERVICE_VERSION=1.0.0
LABEL authors="ubik team"
COPY ./target/ubikservice-${UBIK_SERVICE_VERSION}.jar ubikservice.jar
ENTRYPOINT ["java","-jar","/ubikservice.jar"]
