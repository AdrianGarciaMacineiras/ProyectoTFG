# ##################
# BUILD-BACK
# ##################
FROM openjdk:20-jdk-slim as maven

# Project Build
WORKDIR /opt/JAVA_APP
COPY ./ ./
RUN ./mvnw -B clean package -Dmaven.test.skip=true -Djib.skip=true

# ##################
# FINAL IMAGE
# ##################
FROM openjdk:20-jdk

USER root
WORKDIR /var/www/UBIK

# Copy Jar (from back)
COPY --from=maven /opt/JAVA_APP/target/*.jar ./ubikservice.jar

# CONFIG
ENV NEO4J_URI=
ENV NEO4J_USERBNAME=
ENV NEO4J_PASSWORD=
ENV LOG_LEVEL=


# Minimal healthcheck
HEALTHCHECK --interval=5m --timeout=3s CMD curl -fsi http:/localhost:8080/actuator/health | grep "^HTTP\/" | grep 200 || exit 1

#USER www-data
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ubikservice.jar"]