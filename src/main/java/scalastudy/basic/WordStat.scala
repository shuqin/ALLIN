package scalastudy.basic

import scala.collection.immutable.List
import scala.collection.mutable.{HashMap, Map}
import scalastudy.utils.DefaultFileUtil._
import scalastudy.utils.CollectionUtil.sortByValue
import scalastudy.utils.StringUtil.splitText

/**
 * Created by lovesqcc on 16-3-19.
 */
object WordStat extends App {

  var seps = " -!\"#$%&()*,./:;?@[]^_`{|}~+<=>\\".toArray

  launch()

  def init(): Unit = {
    if (WordStat.seps == null) {
      seps = " -!\"#$%&()*,./:;?@[]^_`{|}~+<=>\\".toArray
    }
  }

  def launch(): Unit = {

    val path = "/home/lovesqcc/work/java/ALLIN/src/main/java/"
    val files = fetchAllFiles(path).filter((file:String) => file.endsWith("java"))
    //files.foreach { println }

    val allWords = files.map(readFile(_)).map(analysisWords(_)).flatten.toList
    sortByValue(statWords(allWords)).map(println)
  }

  def analysisWords(content:String):List[String] = {
    return splitText(content, WordStat.seps);
  }

  def statWords(words: List[String]):Map[String,Int] = {
    val wordsMap = new HashMap[String,Int]
    words.foreach { w =>
      wordsMap(w) = wordsMap.getOrElse(w,0) + 1
    }
    return wordsMap
  }



}
