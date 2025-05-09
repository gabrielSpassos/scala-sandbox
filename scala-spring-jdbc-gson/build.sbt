name := "scala-spring-jdbc-gson"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "3.4.1"
val javaVersion = "21"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "com.h2database" % "h2" % "2.3.232" % Runtime,
  "com.google.code.gson" % "gson" % "2.12.1"
)

javacOptions ++= Seq(
  "--release", javaVersion
)

Compile / mainClass := Some("com.gabrielspassos.Application")