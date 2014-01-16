import sbt._
import Keys._
 
object ApplicationBuild extends Build {
 
  val appName         = "videprinter"
  val appVersion      = "1.01"
 
  val appDependencies = Nil
 
  val main = play.Project(
    appName, appVersion, appDependencies
  ).settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-agent" % "2.1.0",
      "org.quartz-scheduler" % "quartz" % "2.2.0",
      "com.gu" %% "pa-client" % "4.0"
    )
  )
}