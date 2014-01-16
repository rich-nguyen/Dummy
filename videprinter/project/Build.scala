import sbt._
import Keys._
import play.Project._
 
object ApplicationBuild extends Build {
 
  val appName         = "videprinter"
  val appVersion      = "1.01"
 
  val appDependencies = Nil
 
  val main = play.Project(
    appName, appVersion, appDependencies
  ) 
 
}