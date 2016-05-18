package scalastudy.concurrent.actors

import java.io.{File, PrintWriter}

import akka.actor.{ActorRef, Actor}

import scala.io.Source
import scala.collection.immutable.{List}
import scala.collection.mutable.Set

import scalastudy.utils.{PathConstants}


/**
  * Created by shuqin on 16/5/19.
  */
class CheckUnduplicatedNumbersActor(numbers:Int, bigfileSortActor: ActorRef) extends Actor {

    val filename = PathConstants.projPath + "/data/"+numbers+".txt"
    val fwResult = new PrintWriter(new File(filename))

    var count = 0;

    def checkUnduplicatedNumbers(): Unit = {
        println("Expected: " + numbers + " , Actual Received: " + count)
        assert(count == numbers)
        assert(new OnceLoadStrategy().checkUnduplicatedNumbersInFile(filename) == true)
        println("checkUnduplicatedNumbers passed.")
    }

    override def receive: Receive = {

        case numberList: List[Int] =>
            fwResult.write(numberList.mkString(" ") + "\n");
            count += numberList.length

        case (0, Integer.MAX_VALUE) =>
            println("Reach End.")
            fwResult.flush
            fwResult.close
            checkUnduplicatedNumbers
            bigfileSortActor ! filename
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

    trait CheckUnduplicatedStrategy {
        def checkUnduplicatedNumbersInFile(filename:String):Boolean
    }
}
