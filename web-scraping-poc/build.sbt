ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.1"

lazy val root = (project in file("."))
  .settings(
    name := "web-scraping-poc"
  )

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.1.1" % Test,
  "org.jsoup" % "jsoup" % "1.21.1",
  "net.ruippeixotog" %% "scala-scraper" % "3.2.0"
)
