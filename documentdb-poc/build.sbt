import scala.collection.Seq

val scala3Version = "3.6.4"
val javaVersion = "21"
val springBootVersion = "3.4.4"

name := "documentDB-poc"
version := "0.1.0-SNAPSHOT"
scalaVersion := scala3Version

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-data-mongodb" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test,
)

// build.sbt
javaOptions ++= Seq(
  "-source", javaVersion,
  "-target", javaVersion,
  "--enable-preview",
  "--add-opens=java.base/java.lang=ALL-UNNAMED",
  "--add-opens=java.base/java.util=ALL-UNNAMED",
  "--add-opens=java.base/java.math=ALL-UNNAMED"
)