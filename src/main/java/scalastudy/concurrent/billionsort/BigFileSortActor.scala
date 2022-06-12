package scalastudy.concurrent.billionsort

import java.io.{File, PrintWriter}
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalastudy.utils.{CollectionUtil, DefaultFileUtil, PathConstants}


/**
  * Created by shuqin on 16/5/20.
  */
class BigFileSortActor(numbers: Int, actorSystem: ActorSystem) extends Actor with SortChecker {

    override def receive: Receive = {

        case filename:String =>
            println("Received File: " + filename)
            sortFile(filename)
    }

    def produceFuture(line:String): Future[List[List[Int]]] = {
        val origin = line.split("\\s+").map( s => Integer.parseInt(s)).toList
        Future {
            List(origin.sorted)
        }
    }

    def cat(x: List[List[Int]],y:List[List[Int]]): List[List[Int]] = {
        return (x :: y :: Nil).flatten
    }

    def obtainSortedFuture(futureTasks:List[Future[List[List[Int]]]]):Future[List[Int]] = {
        val sortedListsFuture = Future.reduce(futureTasks)(cat(_,_))

        sortedListsFuture map {
            value:List[List[Int]] =>
                CollectionUtil.mergeKOrderedList(value)
        }
    }

    def sortFile(filename:String):Unit = {

        val futureTasks = DefaultFileUtil.readFileLines(filename).map(produceFuture(_))
        println("task numbers: " + futureTasks.size)

        val allNumberSortedFuture = obtainSortedFuture(futureTasks)

        allNumberSortedFuture.onComplete {
            case Success(value:List[Int]) =>
                println("sort finished.")
                writeSorted(value, filename)
                checkSorted(filename, numbers)

                println("sleep 3s and then begin to stop all.")
                TimeUnit.SECONDS.sleep(3)
                actorSystem.shutdown
            case Failure(ex) =>
                println("Sort failed: " + ex.getMessage)
        }
    }

    def writeSorted(allNumberSorted: List[Int], filename: String): Unit = {
        val fwResult = new PrintWriter(new File(filename + ".sorted.txt"))
        fwResult.write(allNumberSorted.mkString("\n"))
        fwResult.flush
        fwResult.close
    }
}

object BigFileSortActorTest {

    def main(args:Array[String]):Unit = {

        val numbers = 10000000
        val system = ActorSystem("BigFileSortActorTest")
        val bigFileSortActor = system.actorOf(Props(new BigFileSortActor(numbers, system)),name="bigFileSortActor")
        bigFileSortActor ! PathConstants.projPath + "/data/" + numbers +".txt"

        TimeUnit.SECONDS.sleep(640)
        system.shutdown

    }

}
