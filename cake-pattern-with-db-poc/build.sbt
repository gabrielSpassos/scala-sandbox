val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "cake-pattern-with-db-poc",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.hibernate" % "hibernate-core" % "5.6.15.Final" pomOnly(),
      "org.hibernate" % "hibernate-entitymanager" % "5.6.15.Final",
      "org.hsqldb" % "hsqldb" % "2.7.2" % Test,
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.specs2" %% "specs2-core" % "5.3.2" % Test,
      "org.mockito" % "mockito-all" % "1.10.19" % Test
    )

  )
