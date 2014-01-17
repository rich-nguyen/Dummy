package services

import akka.util.Timeout
import akka.pattern.ask
import akka.actor.{Actor, Props}

import common.{ExecutionContexts, Logging}
import model.Event

import scala.concurrent.duration._
import scala.language.postfixOps

import play.api.libs.json._
import play.api.libs.concurrent.Akka
import play.api.libs.iteratee._
import play.api.Play.current

object EventStream extends ExecutionContexts {
  implicit private val timeout:Timeout = Timeout(2 seconds)

  private lazy val default = {
    Akka.system.actorOf(Props[EventStream],"FootballEventStream")
  }

  def subscribe(): scala.concurrent.Future[(Iteratee[JsValue,_],Enumerator[JsValue])] = {

    default ? Subscribe map {

      case Connected(enumerator) =>

        // Create an Iteratee to consume(ignore) data from the client.
        val iteratee = Iteratee.ignore[JsValue].map { _ =>
          default ! Quit
        }

        // Use the actor's enumerator to send messages to the client.
        (iteratee,enumerator)

      case CannotConnect(error) =>

        // A finished Iteratee sending EOF
        val iteratee = Done[JsValue,Unit]((),Input.EOF)

        // Send an error and close the socket
        val enumerator =  Enumerator[JsValue](JsObject(Seq("error" -> JsString(error)))).andThen(Enumerator.enumInput(Input.EOF))

        (iteratee,enumerator)
    }
  }

  def publish(content:Seq[model.Event]) {
    default ! Notify(content)
  }
}

class EventStream extends Actor with Logging {

  var members: Int = 0
  val (contentEnumerator, contentChannel) = Concurrent.broadcast[JsValue]

  // Receive requests from clients to subscribe and unsubscribe.
  def receive = {

    case Subscribe => {
      if(members > 200) {
        log.info("Cannot subscribe to football event stream, connections exhausted.")
        sender ! CannotConnect("Max number of connections made")
      } else {
        members = members + 1
        sender ! Connected(contentEnumerator)
        log.info(s"Subscribed to football event stream. Members = $members")
      }
    }

    case Notify(content:Seq[Event]) => {
      // Send new content to all subscribers.
      val msg = JsObject( Seq("events" -> JsArray(content.map(_.toJson()))))

      contentChannel.push(msg)
    }

    case Quit => {
      members = members - 1
      log.info(s"Unsubscribed from football event stream. Members = $members")
    }
  }
}

case class Subscribe()
case class Quit()
case class Notify(content:Seq[model.Event])

case class Connected(enumerator:Enumerator[JsValue])
case class CannotConnect(msg: String)

