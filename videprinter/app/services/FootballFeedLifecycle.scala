package services

import play.api.libs.concurrent.Akka
import play.api.{Play, GlobalSettings, Application }
import play.api.Play.current

import akka.agent.Agent

import common.Jobs

trait FootballFeedLifecycle extends GlobalSettings {
  override def onStart(app: Application) {
    super.onStart(app)

    Jobs.deschedule("FootballFeedRefreshJob")

    // fire every min
    Jobs.schedule("FootballFeedRefreshJob",  "0/10 * * * * ?") {
      FootballFeed.update()
    }

    if (Play.isDev) {
      FootballFeed.update()
    }
  }

  override def onStop(app: Application) {
    Jobs.deschedule("FootballFeedRefreshJob")

    FootballFeed.stop()

    super.onStop(app)
  }
}

object AkkaAgent {
  def apply[T](value: T) = Agent(value)(Akka.system(Play.current))
}