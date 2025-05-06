ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "zero-poc",
    idePackagePrefix := Some("com.gabrielspassos.poc")
  )
