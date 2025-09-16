import sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.MergeStrategy

name := "scala-bitonic-poc"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "3.5.5"
val javaVersion = "21"
val testContainersVersion = "1.21.3"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.7",
  "com.google.code.gson" % "gson" % "2.13.2",
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-redis" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-logging" % springBootVersion % Runtime,
  "io.lettuce" % "lettuce-core" % "6.8.1.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "org.testcontainers" % "testcontainers" % testContainersVersion % Test,
  "org.testcontainers" % "postgresql" % testContainersVersion % Test,
  "com.redis" % "testcontainers-redis" % "2.2.4" % Test,
  "org.testcontainers" % "junit-jupiter" % testContainersVersion % Test
)

dependencyOverrides += "ch.qos.logback" % "logback-classic" % "1.5.18"

javacOptions ++= Seq(
  "--release", javaVersion
)

Compile / mainClass := Some("com.gabrielspassos.Application")

enablePlugins(AssemblyPlugin)
assembly / mainClass := Some("com.gabrielspassos.Application")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "spring", "org.springframework.boot.autoconfigure.AutoConfiguration.imports") =>
    MergeStrategy.concat
  case PathList("META-INF", "spring", xs @ _*) =>
    MergeStrategy.first
  case PathList("META-INF", xs @ _*) =>
    MergeStrategy.discard
  case _ => MergeStrategy.first
}