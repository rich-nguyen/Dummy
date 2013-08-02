package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Space Mish",loginForm))
  }

  def dashboard = Action {
    // Should stop this soon when authentication is in place.
    Ok(views.html.dashboard("Dashboard", List("Rich","Ben","Noin"), spaceship, taskForm))
  }

  def reflect = Action {
    request => Ok("Got a request again: " + request)
  }

  // User submits form
  def login = Action {
    implicit request => loginForm.bindFromRequest.fold(
      formError => BadRequest("error logging in" + formError.errors.mkString(" ")),
      {
        case (username, _) => Ok(views.html.dashboard(  "Dashboard",
                                                        username :: List("Rich","Ben","Noin"),
                                                        spaceship,
                                                        taskForm))
      }
    )
  }

  def newTask = Action {
    implicit request => taskForm.bindFromRequest.fold(
      formError => BadRequest("error with task request" + formError.errors.mkString(" ")),
      {
        case ("add-fuel", value:String) => {
          spaceship = spaceship.addFuel(value.toFloat)
          Redirect(routes.Application.dashboard).flashing(
            "shipInfo" -> "Fuel added."
          )
        }
      }
    )
  }

  def deleteTask(id: Long) = TODO

  val loginForm = Form(
    tuple(
      "name" -> text,
      "password" -> text
    )
  )

  val taskForm = Form(
    tuple(
      "task" -> text,
      "value" -> text
    )
  )

  var spaceship = model.Spaceship("Firefly", 0.0f, 50.0f,Nil)
}