import scala.collection.Seq

val scala3Version = "3.6.4"
val javaVersion = "21"
val springBootVersion = "3.4.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "documentDB-poc",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
      "org.scalameta" %% "munit" % "1.0.0" % Test
    )
  )

javacOptions ++= Seq("-source", "21", "-target", "21")
