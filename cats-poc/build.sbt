val scala3Version = "3.5.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "cats-poc",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.scalameta" %% "munit" % "1.0.0" % Test
    )
  )
