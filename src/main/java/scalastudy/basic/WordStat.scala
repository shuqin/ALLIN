package scalastudy.basic

import scala.collection.immutable.List
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map, HashMap}
import java.io.File

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
    val files = fetchAllJavaFiles(path)
    //files.foreach { println }

    val allWords = files.map(readFile(_)).map(analysisWords(_)).flatten.toList
    sortByValue(statWords(allWords)).map(println)
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


  def readFile(filename:String): String = {
    import scala.io.Source
    val fileSource =  Source.fromFile(filename)
    try {
      return fileSource.mkString
    } finally {
      fileSource.close()
    }
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

  def splitText(text:String, seps:Array[Char]): List[String] = {
    var init = Array(text)
    seps.foreach { sep =>
      init = init.map(_.split(sep)).flatten.map(_.trim).filter(s => s.length > 0)
    }
    return init.toList
  }

  def sortByValue(m: Map[String,Int]): Map[String,Int] = {
    val sortedm = new mutable.LinkedHashMap[String,Int]
    m.toList.sortWith{case(kv1,kv2) => kv1._2 > kv2._2}.foreach { t =>
      sortedm(t._1) = t._2
    }
    return sortedm
  }

}
