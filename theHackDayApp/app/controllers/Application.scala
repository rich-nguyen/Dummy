package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Space Mish",loginForm))
  }

  def dashboard = Action {
    Ok(views.html.dashboard("Dashboard", List("Rich","Ben","Noin")))
  }

  def reflect = Action {
    request => Ok("Got a request again: " + request)
  }

  // User submits form
  def login = Action {
    implicit request =>

      loginForm.bindFromRequest.fold(
        formError =>
          BadRequest("error logging in" + formError.errors.mkString(" ")),
        {
          case (username, password) => Ok(views.html.dashboard("Dashboard", username :: List("Rich","Ben","Noin")))
        }
    )
  }

  // TODO:
  // Show and set persistent data.
  // Who you know and how, ie. invite-only users. OpenID for now.

  def newTask = TODO

  def deleteTask(id: Long) = TODO

  /**
   * Describes the login form.
   */
  val loginForm = Form(
    tuple(
      "name" -> text,
      "password" -> text
    )
  )
  
}