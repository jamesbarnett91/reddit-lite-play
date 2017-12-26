
name := """reddit-lite"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  javaWs,
  "com.github.marlonlom" % "timeago" % "3.0.1"
)
