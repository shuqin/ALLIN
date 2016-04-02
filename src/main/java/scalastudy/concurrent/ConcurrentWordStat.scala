package scalastudy.concurrent

import akka.actor.{ActorSystem, Props}

import scala.collection.mutable.Map
import scalastudy.basic.WordStat
import scalastudy.concurrent.actors.{AnalysisWordActor, FetchJavaFileActor, ReadFileActor, StatWordActor}
import scalastudy.utils.CollectionUtil

/**
 * Created by lovesqcc on 16-3-19.
 */
object ConcurrentWordStat extends App {

  launch()

  def launch(): Unit = {
    val path = "/home/lovesqcc/work/java/ALLIN/src/main/java/"

    val system = ActorSystem("actor-wordstat")
    val statWordActor = system.actorOf(Props[StatWordActor])
    val analysisWordActor = system.actorOf(Props(new AnalysisWordActor(statWordActor)))
    val readFileActor = system.actorOf(Props(new ReadFileActor(analysisWordActor)))
    val fetchFileActor = system.actorOf(Props(new FetchJavaFileActor(readFileActor)))

    fetchFileActor ! path

    Thread.sleep(15000)

    val concurrentResult:Map[String,Int] = CollectionUtil.sortByValue(StatWordActor.finalResult())

    WordStat.init()
    val allWords = WordStat.fetchAllJavaFiles(path)
                           .map(WordStat.readFile(_))
                           .map(WordStat.analysisWords(_)).flatten.toList
    val basicResult:Map[String,Int] = CollectionUtil.sortByValue(WordStat.statWords(allWords))

    // Compare the results of serial version and actors version
    concurrentResult.keySet.foreach { key =>
      assert(concurrentResult(key) == basicResult(key))
    }
    println("All Passed. Yeah ~~ ")

    system.shutdown

  }



}