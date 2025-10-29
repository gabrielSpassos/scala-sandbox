name := "database-migration-poc"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.3"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "3.5.6"
val javaVersion = "25"
val testContainersVersion = "1.21.3"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.8",
  "org.liquibase" % "liquibase-core" % "5.0.1",
  "com.google.code.gson" % "gson" % "2.13.2",
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "org.mockito" % "mockito-core" % "5.20.0" % Test,
  "net.bytebuddy" % "byte-buddy" % "1.17.8" % Test,
  "org.testcontainers" % "testcontainers" % testContainersVersion % Test,
  "org.testcontainers" % "postgresql" % testContainersVersion % Test,
  "org.testcontainers" % "junit-jupiter" % testContainersVersion % Test
)

javacOptions ++= Seq(
  "--release", javaVersion
)

Compile / mainClass := Some("com.gabrielspassos.Application")

Test / parallelExecution := false
