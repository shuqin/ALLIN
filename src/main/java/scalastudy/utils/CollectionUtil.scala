package scalastudy.utils

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Map}

/**
 * Created by lovesqcc on 16-4-2.
 */
object CollectionUtil {

  def main(args: Array[String]): Unit = {

    testMerge
    val map = Map("shuqin" -> 31, "fanfan" -> 26, "yanni" -> 28)
    sortByValue(map).foreach { println }
  }

  def testMerge(): Unit = {
    assert(CollectionUtil.merge(Nil, Nil) == List())
    assert(CollectionUtil.merge(List(), Nil) == List())
    assert(CollectionUtil.merge(List(), List()) == List())
    assert(CollectionUtil.merge(List(), List(1,3)) == List(1,3))
    assert(CollectionUtil.merge(List(4,2), List()) == List(4,2))
    assert(CollectionUtil.merge(List(4,2), Nil) == List(4,2))
    assert(CollectionUtil.merge(List(2,4), List(1,3)) == List(1,2,3,4))
    assert(CollectionUtil.merge(List(2,4), List(1,3,5)) == List(1,2,3,4,5))
    assert(CollectionUtil.merge(List(2,4,6), List(1,3)) == List(1,2,3,4,6))

    assert(CollectionUtil.mergeKOrderedList(Nil) == List())
    assert(CollectionUtil.mergeKOrderedList(List()) == List())
    assert(CollectionUtil.mergeKOrderedList(List(List())) == List())
    assert(CollectionUtil.mergeKOrderedList(List(List(1,2))) == List(1,2))
    assert(CollectionUtil.mergeKOrderedList(List(List(), List())) == List())
    assert(CollectionUtil.mergeKOrderedList(List(List(), List(1,3))) == List(1,3))
    assert(CollectionUtil.mergeKOrderedList(List(List(2,4), List())) == List(2,4))
    assert(CollectionUtil.mergeKOrderedList(List(List(2,4), List(1,3))) == List(1,2,3,4))
    assert(CollectionUtil.mergeKOrderedList(List(List(2,4), List(1,3,5))) == List(1,2,3,4,5))
    assert(CollectionUtil.mergeKOrderedList(List(List(2,4,6), List(1,3))) == List(1,2,3,4,6))
    assert(CollectionUtil.mergeKOrderedList(List(List(2,4,7), List(1,6), List(3,5))) == List(1,2,3,4,5,6,7))
    assert(CollectionUtil.mergeKOrderedList(List(List(2,4,9), List(1,7), List(3,6), List(5,8))) == List(1,2,3,4,5,6,7,8,9))
    println("test merge list passed.")
  }

  /**
   * 对指定 Map 按值排序
   */
  def sortByValue(m: Map[String,Int]): Map[String,Int] = {
    val sortedm = new mutable.LinkedHashMap[String,Int]
    m.toList.sortWith{case(kv1,kv2) => kv1._2 > kv2._2}.foreach { t =>
      sortedm(t._1) = t._2
    }
    return sortedm
  }

  /**
   * 合并两个有序列表
   */
  def merge(xList: List[Int], yList: List[Int]): List[Int] = {
    if (xList.isEmpty) {
      return yList
    }
    if (yList.isEmpty) {
      return xList
    }
    val result = ListBuffer[Int]()
    var xListC = xList
    var yListC = yList
    while (!xListC.isEmpty && !yListC.isEmpty ) {
      if (xListC.head < yListC.head) {
        result.append(xListC.head)
        xListC = xListC.tail
      }
      else {
        result.append(yListC.head)
        yListC = yListC.tail
      }
    }
    if (xListC.isEmpty) {
      result.appendAll(yListC)
    }
    if (yListC.isEmpty) {
      result.appendAll(xListC)
    }

    result.toList
  }

  /**
   * 合并k个有序列表
   */
  def mergeKOrderedList(klists: List[List[Int]]): List[Int] = {
      if (klists.isEmpty) {
        return List[Int]()
      }
      var nlist = klists.size
      if (nlist == 1) {
        return klists.head
      }
      var klistp = klists;
      val kbuf = ListBuffer[List[Int]]()
      while (nlist > 1) {
        for (i <- 0 to nlist/2-1) {
          kbuf.insert(i, merge(klistp(2*i), klistp(2*i+1)))
          if (nlist%2 == 1) {
            kbuf.append(klistp(nlist-1))
          }
        }
        nlist = nlist - nlist/2
        klistp = kbuf.toList
      }

      kbuf.toList.head
  }

}
