package services

import common.{ExecutionContexts, Logging}
import model.{ Competition}

import org.joda.time.{DateMidnight, DateTime}
import pa.{MatchEvent, MatchDay, Http, PaClient}
import scala.concurrent.Future
import play.api.libs.ws.WS

object Client extends PaClient with Http with Logging with ExecutionContexts{
  def apiKey: String = secrets.Secrets.paKey
  override def GET(urlString: String): Future[pa.Response] = {
    //Future(Response(1,"nothing","nothing"))
    val promiseOfResponse = WS.url(urlString).withTimeout(2000).get()

    promiseOfResponse.map{ r =>

      //this feed has a funny character at the start of it http://en.wikipedia.org/wiki/Zero-width_non-breaking_space
      //I have reported to PA, but just trimming here so we can carry on development
      pa.Response(r.status, r.body.dropWhile(_ != '<'), r.statusText)
    }
  }
}

object FootballFeed extends Logging with ExecutionContexts{
  private val matchDays = AkkaAgent[List[MatchDay]](Nil)
  private val matchEvents = AkkaAgent[List[model.Event]](Nil)

  def matches : List[MatchDay] = matchDays.get()
  def events : List[model.Event] = matchEvents.get()

  def update(){
    log.info("grabbing football data")

    // Find all the matches for a given date.
    val date :DateMidnight = /*org.joda.time.DateMidnight.now()*/ DateMidnight.parse("2014-01-11")
    val newMatches = Client.matchDay(Competition.PremierLeague, date)

    newMatches.map( matchList => {
      matchDays.send(matchList)
    })

    // Get all the events from today's matches.
    val allEvents: Future[List[List[model.Event]]] =
      Future.sequence(
        matches.map( fMatch => {
          val futureEvents = Client.matchEvents(fMatch.id)

          futureEvents.map ( events => {
            val matchEvents: List[model.Event] = events.map ( e => {
              e.events.map(model.Event(_,fMatch))
            }).getOrElse(Nil)

            matchEvents.collect {
              case event:model.Goal => event }
          })
        })
      )

    allEvents.map(events => {
      val sortedEvents = events.flatten.sortBy(_.eventTime)
      matchEvents.send(sortedEvents)
      services.EventStream.publish(sortedEvents)
    })
  }

  def stop(){}

}
