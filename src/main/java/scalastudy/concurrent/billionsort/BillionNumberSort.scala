package scalastudy.concurrent.billionsort

import akka.actor.{ActorSystem, Props}

import scalastudy.concurrent.ForkJoinPoolStartup
import scalastudy.concurrent.config.ActorSystemFactory
import scalastudy.concurrent.billionsort.Constants._

/**
  * Created by shuqin on 16/5/18.
  */
object BillionNumberSort extends App {

    launch()

    def launch(): Unit = {
        ForkJoinPoolStartup.start(createActors(), poolWaitSecs)
    }

    def createActors():NumberGeneratorTask = {
        val system:ActorSystem = ActorSystemFactory.newInstance()
        val bigfileSortActor = system.actorOf(Props(new BigFileSortActor(numbers)))
        val checkNumberActor = system.actorOf(Props(new CheckUnduplicatedNumbersActor(numbers, bigfileSortActor)), name="checkNumberActor")
        val numGenTask = new NumberGeneratorTask(numbers, 0, rangeMaxNumber, checkNumberActor)
        return numGenTask
    }

}
