package scalastudy.concurrent.billionsort

import java.io.{File, PrintWriter}

import zzz.study.datastructure.vector.EnhancedBigNBitsVector

import scala.collection.mutable.Set
import scala.io.Source

/**
  * Created by shuqin on 17/4/26.
  */
trait CheckUnduplicatedNumbers {

  def checkUnduplicatedNumbers(filename:String, numbers:Int): Unit = {

    assert(new OnceLoadStrategy().checkUnduplicatedNumbersInFile(filename, numbers) == true)
    println("checkUnduplicatedNumbers passed.")
  }

  /**
    * 一次性加载所有数到内存, 适用于内存可以装下所有数的情况
    * 比如 10000000 个整数占用 40M　空间, 2G 内存是绰绰有余的, 但十亿占用 4G 空间失效
    */
  class OnceLoadStrategy extends CheckUnduplicatedStrategy {

    def checkUnduplicatedNumbersInFile(filename:String, numbers:Int):Boolean = {
      var numbersInFile = 0
      val unDupNumberSet = Set[Int]()
      Source.fromFile(filename).getLines.
        foreach { line =>
          val numbersInLine = line.split("\\s+").map(Integer.parseInt(_)).toSet
          numbersInFile += numbersInLine.size;
          unDupNumberSet ++= numbersInLine
                }
      println("Expected: " + numbers + " , Actual In File: " + numbersInFile)
      println("Unduplicated numbers in File: " + unDupNumberSet.size)
      unDupNumberSet.size == numbers
    }
  }

  /**
    * 使用位图技术来检测不重复的数, 实际上还能用于排序
    * N个数只要 4(N/32+1) = N/8 + 4 个字节
    * 十亿个数只要 125000004B = 125MB
    * 反过来, 内存 1G 的机器可以对 80亿 的不重复数进行排序
    */
  class BitMapStrategy extends CheckUnduplicatedStrategy {

    val nbitsVector = new EnhancedBigNBitsVector(BillionNumberSort.rangeMaxNumber)

    override def checkUnduplicatedNumbersInFile(filename: String, numbers:Int): Boolean = {
      Source.fromFile(filename).getLines.
        foreach { line =>
          val numbersInLine = line.split("\\s+").map(Integer.parseInt(_)).toSet
          numbersInLine.foreach { num =>
            nbitsVector.setBit(num)
          }
        }

      val undupTotal = checkAndSort(filename)
      println("undupTotal: " + undupTotal)
      assert(undupTotal == numbers)
      return true
    }

    def checkAndSort(filename: String): Integer = {
      val fwFinalResult = new PrintWriter(new File(filename+".sorted.txt"))
      val sorted = nbitsVector.expr()
      var undupTotal = sorted.size()
      fwFinalResult.flush()
      fwFinalResult.close()
      return undupTotal
    }

  }

  trait CheckUnduplicatedStrategy {
    def checkUnduplicatedNumbersInFile(filename:String, numbers:Int):Boolean
  }

}
