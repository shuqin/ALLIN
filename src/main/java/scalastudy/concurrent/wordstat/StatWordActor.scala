package scalastudy.concurrent.wordstat

import akka.actor.Actor
import akka.event.Logging

import scala.collection.immutable.List
import scala.collection.mutable.{HashMap, Map}
import scalastudy.concurrent.ActorTerminationMsg

/**
 * Created by lovesqcc on 16-4-2.
 */
object StatWordActor {
  var stat:Map[String,Int] = new HashMap[String,Int]
  def add(newstat:Map[String,Int]) = {
    newstat.foreach { e =>
      stat(e._1) = stat.getOrElse(e._1, 0) + newstat.getOrElse(e._1, 0)
    }
  }
  def finalResult() = stat

  private var recvCount = 0
  private def inc() { recvCount +=1 }
  private def count() = recvCount
}

class StatWordActor extends Actor {

  val log = Logging(context.system, self)

  override def receive: Actor.Receive = {
    case WordListWrapper(wordlist: List[String]) =>
      StatWordActor.inc()
      val stat:Map[String,Int] = statWords(wordlist)
      StatWordActor.add(stat)

    case ActorTerminationMsg =>
      log.info("received times: " + StatWordActor.count())
      context.stop(self)

    case _ =>
      log.info("Unknown received.")
  }

  def statWords(words: List[String]):Map[String,Int] = {
    val wordsMap = new HashMap[String,Int]
    words.foreach { w =>
      wordsMap(w) = wordsMap.getOrElse(w,0) + 1
    }
    return wordsMap
  }
}
