FROM sbtscala/scala-sbt:eclipse-temurin-21.0.8_9_1.11.6_3.7.3 AS builder
WORKDIR /app

# Copy build config separately to cache dependencies
COPY build.sbt .
COPY project/ ./project/

# Now copy the source code
COPY . .

# Build fat JAR
RUN sbt clean assembly

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/scala-*/scala-bitonic-poc.jar /app/scala-bitonic-poc.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "scala-bitonic-poc.jar"]