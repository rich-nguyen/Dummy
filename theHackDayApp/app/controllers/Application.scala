package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Space Mish"))
  }

  def dashboard = Action {
    Ok(views.html.dashboard("Dashboard", List("Rich","Ben","Noin")))
  }

  def reflect = Action {
    request => Ok("Got a request again: " + request)
  }

  // TODO:
  // Show and set persistent data.
  // Who you know and how, ie. invite-only users. OpenID for now.

  def newTask = TODO

  def deleteTask(id: Long) = TODO
  
}