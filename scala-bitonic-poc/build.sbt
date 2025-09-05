name := "scala-bitonic-poc"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "3.5.5"
val javaVersion = "21"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.7",
  "com.google.code.gson" % "gson" % "2.13.1",
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
)

javacOptions ++= Seq(
  "--release", javaVersion
)

Compile / mainClass := Some("com.gabrielspassos.Application")