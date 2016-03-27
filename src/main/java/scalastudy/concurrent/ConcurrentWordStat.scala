package scalastudy.concurrent

import java.lang.Thread

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.actor.Actor.Receive

import scala.collection.immutable.List
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map, HashMap}
import java.io.File

import scalastudy.basic.WordStat

/**
 * Created by lovesqcc on 16-3-19.
 */
object ConcurrentWordStat extends App {

  val seps = " -!\"#$%&()*,./:;?@[]^_`{|}~+<=>\\".toArray

  launch()

  def launch(): Unit = {
    val path = "/home/lovesqcc/work/java/ALLIN/src/main/java/"

    val system = ActorSystem("actor-wordstat")
    val statWordActor = system.actorOf(Props[StatWordActor])
    val analysisWordActor = system.actorOf(Props(new AnalysisWordActor(statWordActor)))
    val readFileActor = system.actorOf(Props(new ReadFileActor(analysisWordActor)))
    val fetchFileActor = system.actorOf(Props(new FetchJavaFileActor(readFileActor)))

    fetchFileActor ! path

    Thread.sleep(6000)

    val concurrentResult:Map[String,Int] = sortByValue(StatWordActor.finalResult())

    WordStat.init()
    val allWords = WordStat.fetchAllJavaFiles(path)
                           .map(WordStat.readFile(_))
                           .map(WordStat.analysisWords(_)).flatten.toList
    val basicResult:Map[String,Int] = sortByValue(WordStat.statWords(allWords))

    // Compare the results of serial version and actors version
    concurrentResult.keySet.foreach { key =>
      assert(concurrentResult(key) == basicResult(key))
    }
    println("All Passed. Yeah ~~ ")

    system.shutdown

  }

  class FetchJavaFileActor(readFileActor: ActorRef) extends Actor {

    override def receive: Actor.Receive = {
      case path:String =>
        val allJavaFiles:Array[String] = fetchAllJavaFiles(path)
        allJavaFiles.foreach {
          readFileActor ! _
        }
    }

    def fileJavaFile(filename:String, suffix:String): Boolean = {
      return filename.endsWith(suffix)
    }

    def fetchAllJavaFiles(path:String): Array[String] = {
      val javaFilesBuf = ArrayBuffer[String]()
      fetchJavaFiles(path, javaFilesBuf)
      return javaFilesBuf.toArray
    }

    def fetchJavaFiles(path:String, javafiles:ArrayBuffer[String]):Unit = {
      val dirAndfiles = new File(path).listFiles
      if (dirAndfiles!=null && dirAndfiles.length > 0) {
        val files = dirAndfiles.filter(_.isFile)
        if (files.length > 0) {
          javafiles ++= files.map(_.getCanonicalPath).filter(fileJavaFile(_,".java"))
        }

        val dirs = dirAndfiles.filter(_.isDirectory)
        if (dirs.length > 0) {
          dirs.map(_.getCanonicalPath).foreach { dirpath =>
            fetchJavaFiles(dirpath, javafiles) }
        }
      }
    }
  }

  // 记录读取的文件数便于核对
  object ReadFileActor {
    private var fileCount = 0
    private def inc() { fileCount +=1 }
    private def count() = fileCount
  }

  class ReadFileActor(analysisWordActor: ActorRef) extends Actor {

    override def receive: Receive = {
      case filename:String =>

        ReadFileActor.inc()
        println("File count: " + ReadFileActor.count())
        println(filename)

        val content = readFile(filename)
        analysisWordActor ! content
    }

    def readFile(filename:String): String = {
      import scala.io.Source
      val fileSource =  Source.fromFile(filename)
      try {
        return fileSource.mkString
      } finally {
        fileSource.close()
      }
    }
  }

  case class WordListWrapper(wordlist: List[String]) {
    def getWordlist = wordlist
  }

  class AnalysisWordActor(statWordActor: ActorRef) extends Actor {

    override def receive: Actor.Receive = {
      case content:String =>
        val words = analysisWords(content)
        statWordActor ! new WordListWrapper(words)
    }

    def analysisWords(content:String):List[String] = {
      return splitText(content, ConcurrentWordStat.seps);
    }

    def splitText(text:String, seps:Array[Char]): List[String] = {
      var init = Array(text)
      seps.foreach { sep =>
        init = init.map(_.split(sep)).flatten.map(_.trim).filter(s => s.length > 0)
      }
      return init.toList
    }
  }

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

    override def receive: Actor.Receive = {
      case WordListWrapper(wordlist: List[String]) =>
        StatWordActor.inc()
        println("received times: " + StatWordActor.count())
        val stat:Map[String,Int] = statWords(wordlist)
        StatWordActor.add(stat)
    }

    def statWords(words: List[String]):Map[String,Int] = {
      val wordsMap = new HashMap[String,Int]
      words.foreach { w =>
        wordsMap(w) = wordsMap.getOrElse(w,0) + 1
      }
      return wordsMap
    }
  }

  def sortByValue(m: Map[String,Int]): Map[String,Int] = {
    val sortedm = new mutable.LinkedHashMap[String,Int]
    m.toList.sortWith{case(kv1,kv2) => kv1._2 > kv2._2}.foreach { t =>
      sortedm(t._1) = t._2
    }
    return sortedm
  }

}