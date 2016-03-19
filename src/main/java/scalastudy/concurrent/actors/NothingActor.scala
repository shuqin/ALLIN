package scalastudy.concurrent.actors

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
 * Created by lovesqcc on 16-4-6.
 */
class NothingActor extends Actor {
  override def receive: Receive = {
    case _ =>
  }
}
