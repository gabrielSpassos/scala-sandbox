name := "reactive-poc"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.4"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "4.0.0"
val javaVersion = "25"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.8",
  "com.google.code.gson" % "gson" % "2.13.2",
  "org.springframework.boot" % "spring-boot-starter-webflux" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.kafka" % "spring-kafka" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "io.projectreactor.kafka" % "reactor-kafka" % "1.3.25",
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "org.mockito" % "mockito-core" % "5.20.0" % Test,
  "net.bytebuddy" % "byte-buddy" % "1.18.2" % Test,
)

javacOptions ++= Seq(
  "--release", javaVersion,
)

Compile / mainClass := Some("com.gabrielspassos.Application")