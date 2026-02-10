name := "scala-last-write-wins"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.8.1"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "4.0.2"
val javaVersion = "25"
val testContainersVersion = "2.0.3"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.9",
  "com.google.code.gson" % "gson" % "2.13.2",
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "org.mockito" % "mockito-core" % "5.21.0" % Test,
  "net.bytebuddy" % "byte-buddy" % "1.18.4" % Test,
  "org.testcontainers" % "testcontainers" % testContainersVersion % Test,
  "org.testcontainers" % "testcontainers-postgresql" % testContainersVersion % Test,
  "org.testcontainers" % "testcontainers-junit-jupiter" % testContainersVersion % Test
)

javacOptions ++= Seq(
  "--release", javaVersion,
)

Compile / mainClass := Some("com.gabrielspassos.Application")