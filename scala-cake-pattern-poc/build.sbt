ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cake-pattern-poc",
    idePackagePrefix := Some("com.gabrielspassos"),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test
  )
