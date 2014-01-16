package controllers

import play.api.mvc._
import play.api.templates.Html
import services.FootballFeed

object Videprinter extends Controller {
  
  def main = Action {
    Ok(views.html.videprinter("Videprinter", FootballFeed.matches, FootballFeed.events, Html("")))
  }
}