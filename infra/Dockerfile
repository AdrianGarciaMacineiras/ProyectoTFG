ARG UBIK_SERVICE_VERSION=1.0.0
FROM eclipse-temurin:20-alpine
LABEL authors="ubik team"
COPY ./target/ubikservice-${UBIK_SERVICE_VERSION}.jar ubikservice.jar
ENTRYPOINT ["java","-jar","/ubikservice.jar"]
