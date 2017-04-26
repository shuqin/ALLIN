package scalastudy.concurrent.billionsort

import java.io.{File, PrintWriter}

import akka.actor.{Actor, ActorRef}

import scala.collection.immutable.List
import scalastudy.utils.PathConstants


/**
  * Created by shuqin on 16/5/19.
  */
class CheckUnduplicatedNumbersActor(val numbers:Int, bigfileSortActor: ActorRef) extends Actor
    with CheckUnduplicatedNumbers {

    val filename = PathConstants.projPath + "/data/"+numbers+".txt"
    val fwResult = new PrintWriter(new File(filename))

    var count = 0
    val useBigFileSort = true

    override def receive: Receive = {

        case numberList: List[Int] =>
            fwResult.write(numberList.mkString(" ") + "\n");
            count += numberList.length

        case (0, BillionNumberSort.rangeMaxNumber) =>
            println("Reach End.")
            println("Expected: " + numbers + " , Actual Received: " + count)
            assert(count == numbers)
            fwResult.flush
            fwResult.close

            checkUnduplicatedNumbers(filename, numbers)
            if (useBigFileSort) {
                bigfileSortActor ! filename
            }
    }

}
