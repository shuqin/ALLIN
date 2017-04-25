package scalastudy.concurrent.billionsort

import scala.io.Source

/**
  * Created by shuqin on 17/4/25.
  */
trait SortChecker {

  /**
    * 每次比较列表的两个数, 后一个不小于前一个
    * NOTE: 使用迭代器模式
    */
  def checkSorted(filename:String, numbers:Int): Unit = {
    var last = 0
    var count = 0
    val numIterator = Source.fromFile(filename + ".sorted.txt").getLines().map(line => Integer.parseInt(line.trim))
    checkSort(numIterator, numbers)
    println("test sorted passed.")
  }

  def checkSort(numIterator: Iterator[Int], numbers:Int): Unit = {
    assert(numIterator.size == numbers)
    var last = 0
    numIterator.foreach {
      num =>
        assert(num >= last)
        last = num
    }
  }

  def checkSort(numList: List[Int], numbers:Int): Unit = {
    checkSort(numList.iterator, numbers)
  }
}
