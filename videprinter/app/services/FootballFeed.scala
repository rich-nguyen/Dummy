package services

import common.{ExecutionContexts, Logging}
import model.FootballMatch

import org.joda.time.DateTime
import pa.{Http, PaClient, Response}
import scala.concurrent.Future

case class FootballFeedQuery(
  date: DateTime
)

object Client extends PaClient with Http with Logging with ExecutionContexts{
  def apiKey: String = secrets.Secrets.paKey
  override def GET(urlString: String): Future[pa.Response] = {
    Future(Response(1,"nothing","nothing"))
  }
}

object FootballFeed extends Logging{
  private val matches = AkkaAgent[List[FootballMatch]](Nil)

  def update(){
    log.info("grabbing football data")

    // Find all the matches for a given date.

  }

  def stop(){}
}
