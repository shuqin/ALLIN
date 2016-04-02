package scalastudy.utils

import scala.collection.mutable
import scala.collection.mutable.Map

/**
 * Created by lovesqcc on 16-4-2.
 */
object CollectionUtil {

  def main(args: Array[String]): Unit = {
    val map = Map("shuqin" -> 31, "fanfan" -> 26, "yanni" -> 28)
    sortByValue(map).foreach { println }
  }

  def sortByValue(m: Map[String,Int]): Map[String,Int] = {
    val sortedm = new mutable.LinkedHashMap[String,Int]
    m.toList.sortWith{case(kv1,kv2) => kv1._2 > kv2._2}.foreach { t =>
      sortedm(t._1) = t._2
    }
    return sortedm
  }

}
