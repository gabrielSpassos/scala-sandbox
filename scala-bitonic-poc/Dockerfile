# Stage 1: Build the Scala application
FROM openjdk:21-jdk-slim AS builder
WORKDIR /app

# Copy sbt build files and project source
COPY build.sbt .
COPY project/ project/
COPY src/ src/

# Build the assembly JAR
# Ensure sbt-assembly is added to your plugins.sbt
RUN sbt clean assembly

# Stage 2: Create the final lightweight image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built assembly JAR from the builder stage
COPY --from=builder /app/target/scala-*/scala-bitonic-poc.jar .

# Expose any ports your application listens on (e.g., for a web server)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "scala-bitonic-poc.jar"]