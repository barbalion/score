name := "score"

version := "0.1"

organization := "com.barbalion"

organizationName := "Barbalion"

organizationHomepage := Some(new URL("http://barbalion.com"))

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" % "scala-xml_2.11" % "latest.release",
  "org.scala-lang" % "scala-parser-combinators" % "latest.release",
  "antlr" % "antlr" % "latest.release",
  "commons-io" % "commons-io" % "latest.release",
  "org.antlr" % "ST4" % "latest.release",
  "org.scalatest" %% "scalatest" % "latest.release" % "test"
)

isSnapshot := true

exportJars := true
