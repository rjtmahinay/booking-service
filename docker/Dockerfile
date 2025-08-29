####
# This Dockerfile is used to build a container that runs the Spring Boot application on OpenShift
#
# Build the image with:
#   docker build -f docker/Dockerfile -t booking-service:latest .
#
# OpenShift will automatically build and deploy this using the BuildConfig in deploy.yaml
####

# Build stage
FROM registry.access.redhat.com/ubi8/openjdk-17:1.18 AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Set execute permissions on mvnw and ensure .mvn/wrapper has correct permissions
RUN chmod +x mvnw && \
    chmod -R 777 .mvn/wrapper

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application and create a thin JAR for better layer caching
RUN ./mvnw clean package -DskipTests

# Runtime stage - Using UBI minimal for smaller image size
FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:1.18

# Set Java options for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0"
ENV AB_JOLOKIA_OFF="true"
ENV AB_PROMETHEUS_JMX_EXPORTER_CONFIG="/opt/jmx_exporter/config.yml"

# Create app directory and set permissions for OpenShift
RUN mkdir -p /deployments && \
    chgrp -R 0 /deployments && \
    chmod -R g=u /deployments && \
    chmod -R g+rwX /deployments

# Copy the application from builder image
COPY --from=builder /app/target/*.jar /deployments/app.jar

# Set working directory and user
WORKDIR /deployments
USER 1001

# Expose the port the app runs on (must match service/route in OpenShift)
EXPOSE 8081

# Run the application with recommended JVM settings for containers
ENTRYPOINT ["java", "-jar", "/deployments/app.jar"]