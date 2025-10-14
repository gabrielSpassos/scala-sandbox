import sbt.Keys.libraryDependencies

import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.3"

val javaVersion = "25"

lazy val root = (project in file("."))
  .settings(
    name := "memcached-poc",
    libraryDependencies ++= Seq(
      "com.googlecode.xmemcached" % "xmemcached" % "2.4.8"
    )
  )

javacOptions ++= Seq(
  "--release", javaVersion,
)
