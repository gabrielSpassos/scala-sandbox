name := "tags-poc"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.3"

ThisBuild / organization := "com.gabrielspassos"

val springBootVersion = "4.0.0"
val javaVersion = "25"
val testContainersVersion = "1.21.3"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.7.8",
  "com.google.code.gson" % "gson" % "2.13.2",
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.testcontainers" % "testcontainers" % "1.21.4" % Test,
  "org.testcontainers" % "postgresql" % "1.21.4" % Test,
  "org.testcontainers" % "junit-jupiter" % "1.21.4" % Test,
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
  "org.mockito" % "mockito-core" % "5.21.0" % Test,
  "net.bytebuddy" % "byte-buddy" % "1.18.2" % Test,
  "net.bytebuddy" % "byte-buddy-agent" % "1.18.2" % Test,
)

javacOptions ++= Seq(
  "--release", javaVersion
)

Compile / mainClass := Some("com.gabrielspassos.Application")

Test / parallelExecution := false

Test / fork := true

Test / javaOptions ++= {
  val byteBuddyAgent = (Test / dependencyClasspath).value
    .find(_.data.getName.contains("byte-buddy-agent"))
    .map(_.data.getAbsolutePath)
    .map(path => s"-javaagent:$path")
  byteBuddyAgent.toSeq ++ Seq("-XX:+EnableDynamicAgentLoading")
}
