ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.2"

lazy val root = (project in file("."))
  .settings(
    name := "bitonic-sequence-poc",
    idePackagePrefix := Some("com.gabrielspassos")
  )
