ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "jacoco-poc"
  )

libraryDependencies ++= Seq(
  "org.junit.jupiter" % "junit-jupiter" % "5.12.2" % Test,
  "org.mockito" % "mockito-junit-jupiter" % "5.17.0" % Test,
  "com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test
)

//jacocoExcludes := Seq(
//  "com.gabrielspassos.entity.*"
//)

jacocoIncludes := Seq(
  "com.gabrielspassos.service.*", "com.gabrielspassos.repository.*"
)

jacocoReportSettings := JacocoReportSettings(
  "Jacoco Coverage Report",
  None,
  JacocoThresholds(),
  Seq(JacocoReportFormats.ScalaHTML),
  "utf-8")
