import sbt._

object MiniApps extends Build
{
  lazy val optaFeedMonkey = Project("OptaFeedMonkey", file("OptaFeedMonkey"))
}
