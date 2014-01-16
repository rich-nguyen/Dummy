package controllers

import play.api.mvc._
import play.api.templates.Html

object Videprinter extends Controller {
  
  def main = Action {
    Ok(views.html.videprinter("Videprinter", Html("")))
  }
}