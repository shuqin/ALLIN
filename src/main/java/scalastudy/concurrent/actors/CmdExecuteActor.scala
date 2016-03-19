package scalastudy.concurrent.actors

import akka.actor.{Actor, ActorSystem, Props}

import scala.sys.process._
import scalastudy.concurrent.config.ActorSystemFactory

/**
 * Created by lovesqcc on 16-4-6.
 */

object CmdExecuteActorTest extends App {

  launch()

  def launch(): Unit = {
    val system:ActorSystem = ActorSystemFactory.newInstance()
    val actor = system.actorOf(Props[CmdExecuteActor])
    actor ! "ls -l"

    Thread.sleep(3000)
    system.shutdown
  }

}

class CmdExecuteActor extends Actor {
  override def receive: Receive = {
    case cmd:String =>
      val res = cmd !
    case _ => println("Unknown cmd.")
  }
}
