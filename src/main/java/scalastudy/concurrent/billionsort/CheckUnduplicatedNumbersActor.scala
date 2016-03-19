package scalastudy.concurrent.billionsort

import java.io.{File, PrintWriter}

import akka.actor.{Actor, ActorRef}

import scala.collection.immutable.List
import scalastudy.concurrent.billionsort.Constants.filename

/**
  * Created by shuqin on 16/5/19.
  */
class CheckUnduplicatedNumbersActor(val numbers:Int, bigfileSortActor: ActorRef) extends Actor
    with CheckUnduplicatedNumbers {


    val fwResult = new PrintWriter(new File(filename))

    var count = 0
    val useBigFileSort = true

    override def receive: Receive = {

        case numberList: List[Int] =>
            fwResult.write(numberList.mkString(" ") + "\n");
            count += numberList.length

        case (0, Constants.rangeMaxNumber) =>
            println("Reach End.")
            println("Expected: " + numbers + " , Actual Received: " + count)
            assert(count == numbers)
            fwResult.flush
            fwResult.close

            checkUnduplicatedNumbers(filename, numbers)
            if (useBigFileSort) {
                bigfileSortActor ! filename
            }

        case _ => println("未知消息，请检查原因 ！")
    }

}
