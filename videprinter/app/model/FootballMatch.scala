package model

object Competition {
  val PremierLeague = "100"
}

class Event(
  event : pa.MatchEvent,
  fMatch : pa.MatchDay
){

  private val text:String = event.eventType
  protected val matchTime = event.matchTime.getOrElse("00:00")
  protected val eventId = event.id.getOrElse("00")

  protected val scoreline = fMatch.homeTeam.name + " " + fMatch.homeTeam.score.getOrElse(0) + " - " +
                            fMatch.awayTeam.name + " " + fMatch.awayTeam.score.getOrElse(0)

  lazy val eventTime = event.eventTime.getOrElse("00:00")
  def makeId() : String = {
    matchTime + eventId
  }

  override def toString() = text

  def toJson() = {

  }
}

class Goal(
  event : pa.MatchEvent,
  fMatch : pa.MatchDay
) extends Event(event, fMatch)
{
  private val scorer:String = event.players.headOption.map(_.name).getOrElse("Pele")

  private lazy val isHomeTeamGoal = {
    fMatch.homeTeam.id == event.teamID.getOrElse("")
  }

  override def toString() = {
    if (isHomeTeamGoal) {
      s"GOAL!!! (${scorer} ${matchTime}) ${scoreline}"
    } else {
      s"GOAL!!! ${scoreline} (${scorer} ${matchTime})"
    }
  }

}

object Event {
  def apply(event: pa.MatchEvent, fMatch: pa.MatchDay): Event = {
    event.eventType match {
      case "timeline" => new Event(event, fMatch)
      case "goal" => new Goal(event, fMatch)
      case _ => new Event(event, fMatch)
    }
  }
}