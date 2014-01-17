package controllers

import play.api.mvc._
import play.api.templates.Html
import services.FootballFeed
import play.api.libs.json.JsValue

object Videprinter extends Controller {
  
  def main = Action {
    Ok(views.html.videprinter("Videprinter", FootballFeed.matches, FootballFeed.events, Html("")))
  }

  def eventStream = WebSocket.async[JsValue] { request =>
    services.EventStream.subscribe()
  }
}