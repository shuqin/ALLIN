package scalastudy.concurrent.actors

import java.io.{File, PrintWriter}

import akka.actor.{ActorRef, Actor}
import zzz.study.datastructure.vector.NBitsVector

import scala.io.Source
import scala.collection.immutable.{List}
import scala.collection.mutable.Set
import scalastudy.concurrent.forkjoin.BillionNumberSort

import scalastudy.utils.{PathConstants}


/**
  * Created by shuqin on 16/5/19.
  */
class CheckUnduplicatedNumbersActor(numbers:Int, bigfileSortActor: ActorRef) extends Actor {

    val filename = PathConstants.projPath + "/data/"+numbers+".txt"
    val fwResult = new PrintWriter(new File(filename))

    var count = 0
    val useBigFileSort = false

    def checkUnduplicatedNumbers(): Unit = {
        println("Expected: " + numbers + " , Actual Received: " + count)
        assert(count == numbers)
        assert(new BitMapStrategy().checkUnduplicatedNumbersInFile(filename) == true)
        println("checkUnduplicatedNumbers passed.")
    }

    override def receive: Receive = {

        case numberList: List[Int] =>
            fwResult.write(numberList.mkString(" ") + "\n");
            count += numberList.length

        case (0, BillionNumberSort.rangeMaxNumber) =>
            println("Reach End.")
            fwResult.flush
            fwResult.close
            checkUnduplicatedNumbers
            if (useBigFileSort) {
                bigfileSortActor ! filename
            }
    }

    /**
     * 一次性加载所有数到内存, 适用于内存可以装下所有数的情况
     * 比如 10000000 个整数占用 40M　空间, 2G 内存是绰绰有余的, 但十亿占用 4G 空间失效
     */
    class OnceLoadStrategy extends CheckUnduplicatedStrategy {
        def checkUnduplicatedNumbersInFile(filename:String):Boolean = {
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

        val nbitsVector = new NBitsVector(BillionNumberSort.rangeMaxNumber)

        override def checkUnduplicatedNumbersInFile(filename: String): Boolean = {
            Source.fromFile(filename).getLines.
                foreach { line =>
                    val numbersInLine = line.split("\\s+").map(Integer.parseInt(_)).toSet
                    numbersInLine.foreach { num =>
                        nbitsVector.setBit(num)
                    }
                }

            val undupTotal = checkAndSort
            println("undupTotal: " + undupTotal)
            assert(undupTotal == numbers)
            return true
        }

        def checkAndSort(): Integer = {
            val fwFinalResult = new PrintWriter(new File(filename+".sorted.txt"))
            val charArrayStr = nbitsVector.toString
            val arrlen = charArrayStr.length
            var undupTotal = 0
            for (ind <- 0 to arrlen-1) {
                if ("1".equals(charArrayStr.charAt(ind).toString)) {
                    fwResult.write(ind + "\n")
                    undupTotal += 1
                }
            }
            fwFinalResult.flush()
            fwFinalResult.close()
            return undupTotal
        }

    }

    trait CheckUnduplicatedStrategy {
        def checkUnduplicatedNumbersInFile(filename:String):Boolean
    }
}
