package scalastudy.concurrent

import akka.actor.{Actor, ActorSystem, Props}

/**
 * Created by lovesqcc on 16-3-21.
 */

object HelloActorDemo extends App {

  launch()

  def launch(): Unit = {
    val system = ActorSystem("HelloSystem")
    val helloActor = system.actorOf(Props[HelloActor],name="helloActor")
    helloActor ! "hello"

    Thread.sleep(1000)
    system.shutdown
  }

  class HelloActor extends Actor {
    override def receive: Receive = {
      case msg =>
        println(msg)
        println(self)
        println(this)
    }
  }

}


