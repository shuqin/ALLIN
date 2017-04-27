package scalastudy.concurrent.billionsort

import java.util.concurrent.{ForkJoinPool, TimeUnit}

import akka.actor.{ActorSystem, Props}

import scalastudy.concurrent.config.ActorSystemFactory

/**
  * Created by shuqin on 16/5/18.
  */
object BillionNumberSort extends App {

    val rangeMaxNumber = 100000000  // 生成的整数中不超过的最大数
    val numbers = 10000000          // 在 [0, rangeMaxNumber] 生成 numbers 个不重复的整数

    launch()

    def launch(): Unit = {
        start(createActors())
    }

    def createActors():NumberGeneratorTask = {
        val system:ActorSystem = ActorSystemFactory.newInstance()
        val bigfileSortActor = system.actorOf(Props(new BigFileSortActor(numbers)))
        val checkNumberActor = system.actorOf(Props(new CheckUnduplicatedNumbersActor(numbers, bigfileSortActor)), name="checkNumberActor")
        val numGenTask = new NumberGeneratorTask(numbers, 0, rangeMaxNumber, checkNumberActor)
        return numGenTask
    }

    def start(entranceTask:NumberGeneratorTask):Unit = {
        val pool = new ForkJoinPool()
        pool.execute(entranceTask)
        pool.shutdown
        pool.awaitTermination(1024, TimeUnit.SECONDS)
        pool.shutdownNow
    }

}
