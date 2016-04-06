package scalastudy.concurrent

import akka.actor.{ActorSystem, Props}

import scala.collection.mutable.Map
import scalastudy.basic.WordStat
import scalastudy.concurrent.actors.{AnalysisWordActor, FetchJavaFileActor, ReadFileActor, StatWordActor}
import scalastudy.concurrent.config.ActorSystemFactory
import scalastudy.utils.CollectionUtil

/**
 * Created by lovesqcc on 16-3-19.
 */
object ConcurrentWordStat extends App {

  launch()

  def launch(): Unit = {
    val path = "/home/lovesqcc/work/java/ALLIN/src/main/java/"

    val system:ActorSystem = ActorSystemFactory.newInstance()

    val statWordActor = system.actorOf(Props[StatWordActor], name="statWordActor")
    val analysisWordActor = system.actorOf(Props(new AnalysisWordActor(statWordActor)), name="analysisWordActor")
    val readFileActor = system.actorOf(Props(new ReadFileActor(analysisWordActor)), name="readFileActor")
    val fetchFileActor = system.actorOf(Props(new FetchJavaFileActor(readFileActor)), name="fetchFileActor")

    fetchFileActor ! path

    Thread.sleep(15000)

    val concurrentResult:Map[String,Int] = CollectionUtil.sortByValue(StatWordActor.finalResult())

    WordStat.init()
    val allWords = WordStat.fetchAllJavaFiles(path)
                           .map(WordStat.readFile(_))
                           .map(WordStat.analysisWords(_)).flatten.toList
    val basicResult:Map[String,Int] = CollectionUtil.sortByValue(WordStat.statWords(allWords))

    try {
      // Compare the results of serial version and actors version
      basicResult.keySet.foreach { key =>
        println(key + " => " + basicResult(key))
        assert(concurrentResult(key) == basicResult(key))
      }
      println("All Passed. Yeah ~~ ")
    }
    finally {
      system.shutdown
    }
  }
}