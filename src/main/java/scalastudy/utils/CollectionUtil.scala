package scalastudy.utils

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Map}

import scala.math.pow

/**
  * Created by lovesqcc on 16-4-2.
  */
object CollectionUtil {

  def main(args: Array[String]): Unit = {

    testSortByValue
    testAllMergeIsRight

    testPerf(merge)
    testPerf(mergeInplace)
    // testPerf(mergeFunctional)
  }

  def testSortByValue():Unit = {
    val map = Map("shuqin" -> 31, "yanni" -> 28)
    sortByValue(map).foreach { println }
  }

  def testAllMergeIsRight(): Unit = {
    testMerge(merge)
    testMerge(mergeFunctional)
    testMerge(mergeInplace)

    testMergeKOrderedList(mergeKOrderedList)
    testMergeKOrderedList(mergeKOrderedListFunctional)
    testMergeKOrderedList(mergeKOrderedListIneffective)
  }

  def testMerge(merge: (List[Int], List[Int]) => List[Int]):Unit = {
    assert(merge(Nil, Nil) == List())
    assert(merge(List(), Nil) == List())
    assert(merge(List(), List()) == List())
    assert(merge(List(), List(1,3)) == List(1,3))
    assert(merge(List(4,2), List()) == List(4,2))
    assert(merge(List(4,2), Nil) == List(4,2))
    assert(merge(List(2,4), List(1,3)) == List(1,2,3,4))
    assert(merge(List(2,4), List(1,3,5)) == List(1,2,3,4,5))
    assert(merge(List(2,4,6), List(1,3)) == List(1,2,3,4,6))
    assert(merge(List(2,4,6), List(8,10)) == List(2,4,6,8,10))
    println("test merge list passed.")
  }

  def testMergeKOrderedList(mergeKOrderedList: List[List[Int]] => List[Int]):Unit = {
    assert(mergeKOrderedList(Nil) == List())
    assert(mergeKOrderedList(List()) == List())
    assert(mergeKOrderedList(List(List())) == List())
    assert(mergeKOrderedList(List(List(1,2))) == List(1,2))
    assert(mergeKOrderedList(List(List(), List())) == List())
    assert(mergeKOrderedList(List(List(), List(1,3))) == List(1,3))
    assert(mergeKOrderedList(List(List(2,4), List())) == List(2,4))
    assert(mergeKOrderedList(List(List(2,4), List(1,3))) == List(1,2,3,4))
    assert(mergeKOrderedList(List(List(2,4), List(1,3,5))) == List(1,2,3,4,5))
    assert(mergeKOrderedList(List(List(2,4,6), List(1,3))) == List(1,2,3,4,6))
    assert(mergeKOrderedList(List(List(2,4,7), List(1,6), List(3,5))) == List(1,2,3,4,5,6,7))
    assert(mergeKOrderedList(List(List(2,4,9), List(1,7), List(3,6), List(5,8))) == List(1,2,3,4,5,6,7,8,9))
    println("test mergeKOrderedList passed.")
  }

  def testPerf(merge: (List[Int], List[Int]) => List[Int]):Unit = {
    val n = 10
    val numbers = (1 to 7).map(pow(n,_).intValue)
    println(numbers)
    numbers.foreach {
      num =>
        val methodName = merge.toString()
        val start = System.currentTimeMillis
        val xList = (1 to num).filter(_ % 2 == 0).toList
        val yList = (1 to num).filter(_ % 2 == 1).toList
        val merged = merge(xList, yList)
        val mergedSize = merged.size
        val end = System.currentTimeMillis
        val cost = end - start
        println(s"method=${methodName}, numbers=${num}, merged size: ${mergedSize}, merge cost: ${cost} ms")
    }
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
    * 将 yList 合并到 xList 上
    * 结合了 mergeFunctional 和 mergeIneffective 的优势
    * 没有空间开销，时间复杂度为 O(n+m), n,m 分别是 xList, yList 的列表长度
    * TODO not inplace
    */
  def mergeInplace(xList: List[Int], yList: List[Int]): List[Int] = {
    (xList, yList) match {
      case (Nil, Nil) => List[Int]()
      case (Nil, _) => yList
      case (_, Nil) => xList
      case (hx :: xtail, hy :: ytail) =>
        var result = List[Int]()
        var xListP = List[Int]()
        var yListP = List[Int]()
        if (hx > hy) {
          result = hy :: Nil
          xListP = xList
          yListP = ytail
        }
        else {
          result = hx:: Nil
          yListP = yList
          xListP = xtail
        }
        while (xListP != Nil && yListP != Nil) {
          if (xListP.head > yListP.head) {
            result = result :+ yListP.head
            yListP = yListP.tail
          }
          else {
            result = result :+ xListP.head
            xListP = xListP.tail
          }
        }
        if (xListP == Nil) {
          result = result ::: yListP
        }
        if (yListP == Nil) {
          result = result ::: xListP
        }
        // println("xsize=" + xList.size + ", ysize= " + yList.size + ", merged=" + result.size)
        result
    }
  }

  /**
    * 合并两个有序列表
    * 将 yList 合并到 xList 上，没有列表复制操作，没有额外空间开销
    * 但由于每次插入 yList 元素到 xList 都要从头遍历，因此算法时间复杂度是 O(n*m)
    */
  def mergeFunctional(xList: List[Int], yList: List[Int]): List[Int] = {
    (xList, yList) match {
      case (Nil, Nil) => List[Int]()
      case (Nil, _) => yList
      case (_, Nil) => xList
      case (hx :: xtail, hy :: ytail) =>
        yList.foldLeft(xList)(insert)
    }
  }

  def insert(xList:List[Int], y:Int): List[Int] = {
    (xList, y) match {
      case (Nil, _) => y :: Nil
      case (hx :: xtail, _) =>
        if (hx > y) {
          y :: xList
        }
        else {
          var result = hx :: Nil
          var pCurr = xtail
          while (pCurr != Nil && pCurr.head < y) {
            result = result :+ pCurr.head
            pCurr = pCurr.tail
          }
          (result :+ y) ::: pCurr
        }
    }
  }

  /**
    * 合并两个有序列表
    * 将 yList 与 xList 合并到一个全新的链表上
    * 由于使用指针是渐进地合并，因此算法时间复杂度是 O(n+m) n,m 分别是 xList, yList 的列表长度
    * 由于有列表复制操作，且是渐进地合并，因此算法空间复杂度也是 O(n+m)
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
    // println("xsize=" + xList.size + ", ysize= " + yList.size + ", merged=" + result.size)
    result.toList
  }

  /**
    * 合并k个有序列表
    * 转化为并行容器进行并行地合并，有空间开销
    */
  def mergeKOrderedList(klists: List[List[Int]]): List[Int] = {
    if (klists.isEmpty) { return List[Int]() }
    if (klists.size == 1) { return klists.head }
    klists.par.reduce(merge)
  }

  /**
    * 合并k个有序列表
    * 使用函数式逐个地合并,无空间开销
    */
  def mergeKOrderedListFunctional(klists: List[List[Int]]): List[Int] = {
    if (klists.isEmpty) { return List[Int]() }
    if (klists.size == 1) { return klists.head }
    klists.reduce(merge)
  }

  /**
    * 合并k个有序列表
    * 使用插入逐个地合并，空间开销很大
    */
  def mergeKOrderedListIneffective(klists: List[List[Int]]): List[Int] = {
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