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
    val numIterator = Source.fromFile(filename + ".sorted.txt").getLines().map(line => Integer.parseInt(line.trim))
    checkSort(numIterator, numbers)
    println("test sorted passed.")
  }

  /**
    * 函数式实现
    */
  def checkSort(numIterator: Iterator[Int], numbers:Int):Unit = {
    var count = 1
    numIterator.reduceLeft((prev,next) => {
      assert(prev <= next); count += 1 ; next;
    } )
    assert(count == numbers)
  }

  /**
    * 过程式实现
    */
  def checkSortProcedural(numIterator: Iterator[Int], numbers:Int): Unit = {
    var last = 0
    var count = 0
    numIterator.foreach {
      num =>
        assert(num >= last)
        last = num
        count += 1
    }
    assert(count == numbers)
  }

  def checkSort(numList: List[Int], numbers:Int): Unit = {
    checkSort(numList.iterator, numbers)
  }

}
