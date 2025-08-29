####
# This Dockerfile is used to build a container that runs the Spring Boot application on OpenShift
#
# OpenShift will automatically build and deploy this using the BuildConfig in deploy.yaml
####

# Build stage
FROM registry.access.redhat.com/ubi8/openjdk-17:1.18 AS builder

# Set working directory and ensure it's writable
WORKDIR /app

# Copy only the files needed for dependency resolution first
COPY pom.xml .

# Install Maven wrapper and dependencies
RUN \
    # Install Maven
    microdnf install -y maven && \
    # Clean up to reduce image size
    microdnf clean all && \
    rm -rf /var/cache/yum && \
    # Create .m2 directory and set permissions
    mkdir -p $HOME/.m2 && \
    # Download dependencies
    mvn -B dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:1.18

# Set Java options for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0 -Djava.security.egd=file:/dev/./urandom"
ENV AB_JOLOKIA_OFF="true"

# Create app directory with OpenShift compatible permissions
RUN \
    mkdir -p /deployments && \
    chown -R 1001:0 /deployments && \
    chmod -R g=u /deployments && \
    chmod -R g+rwX /deployments

# Copy the application from builder image
COPY --from=builder --chown=1001:0 /app/target/*.jar /deployments/app.jar

# Set working directory and user
WORKDIR /deployments
USER 1001

# Expose the port the app runs on (must match service/route in OpenShift)
EXPOSE 8081

# Run the application with recommended JVM settings for containers
ENTRYPOINT ["java", "$JAVA_OPTS", "-jar", "/deployments/app.jar"]