package controllers

import play.api.mvc._
import services.FootballFeed
import play.api.libs.json.JsValue

object Videprinter extends Controller {
  
  def main = Action {
    Ok(views.html.videprinter("Videprinter", FootballFeed.matches, FootballFeed.events))
  }

  def eventStream = WebSocket.async[JsValue] { request =>
    services.EventStream.subscribe()
  }
}