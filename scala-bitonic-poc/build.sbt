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
  "io.lettuce" % "lettuce-core" % "6.8.1.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "org.testcontainers" % "testcontainers" % testContainersVersion % Test,
  "org.testcontainers" % "postgresql" % testContainersVersion % Test,
  //"org.testcontainers" % "redis" % testContainersVersion % Test,
  "com.redis" % "testcontainers-redis" % "2.2.4" % Test,
  "org.testcontainers" % "junit-jupiter" % testContainersVersion % Test
)

javacOptions ++= Seq(
  "--release", javaVersion
)

Compile / mainClass := Some("com.gabrielspassos.Application")

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) =>
    xs match {
      case ("MANIFEST.MF" :: Nil)                          => MergeStrategy.discard
      case ("spring.factories" :: Nil)                     => MergeStrategy.concat
      case ("spring" :: "aot.factories" :: Nil)            => MergeStrategy.concat
      case ("spring-configuration-metadata.json" :: Nil)   => MergeStrategy.concat
      case ("additional-spring-configuration-metadata.json" :: Nil) =>
        MergeStrategy.concat
      case ("io.netty.versions.properties" :: Nil)         => MergeStrategy.first
      case ("web-fragment.xml" :: Nil)                     => MergeStrategy.discard
      case _                                               => MergeStrategy.discard
    }

  // Java module descriptors — just drop them
  case "module-info.class" => MergeStrategy.discard

  // Avoid duplicate reference.conf / application.conf files
  case x if x.endsWith("reference.conf")   => MergeStrategy.concat
  case x if x.endsWith("application.conf") => MergeStrategy.concat

  // In general, prefer the first one
  case _ => MergeStrategy.first
}