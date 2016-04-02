package scalastudy.concurrent.actors

import akka.actor.{Actor, ActorRef}

import scala.collection.immutable.List

/**
 * Created by lovesqcc on 16-4-2.
 */
case class WordListWrapper(wordlist: List[String]) {
  def getWordlist = wordlist
}

class AnalysisWordActor(statWordActor: ActorRef) extends Actor {

  val seps = " -!\"#$%&()*,./:;?@[]^_`{|}~+<=>\\".toArray

  override def receive: Actor.Receive = {
    case content:String =>
      val words = analysisWords(content)
      statWordActor ! new WordListWrapper(words)
  }

  def analysisWords(content:String):List[String] = {
    return splitText(content, this.seps);
  }

  def splitText(text:String, seps:Array[Char]): List[String] = {
    var init = Array(text)
    seps.foreach { sep =>
      init = init.map(_.split(sep)).flatten.map(_.trim).filter(s => s.length > 0)
    }
    return init.toList
  }
}