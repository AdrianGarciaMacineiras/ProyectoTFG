ARG UBIK_SERVICE_VERSION=1.0.0
FROM eclipse-temurin:20-alpine
LABEL authors="ubik team"
COPY ./target/ubikservice-$UBIK_SERVICE_VERSION.jar ubikservice-$UBIK_SERVICE_VERSION.jar
ENTRYPOINT ["java","-jar","/ubikservice-1.0.0.jar"]
