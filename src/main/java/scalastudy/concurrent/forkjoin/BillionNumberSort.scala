package scalastudy.concurrent.forkjoin

import java.util.concurrent.{TimeUnit, ForkJoinPool}

import akka.actor.{Props, ActorSystem}

import scalastudy.concurrent.actors.{BigFileSortActor, CheckUnduplicatedNumbersActor}
import scalastudy.concurrent.config.ActorSystemFactory

/**
  * Created by shuqin on 16/5/18.
  */
object BillionNumberSort extends App {

    val rangeMaxNumber = 1000000000  // 生成的整数中不超过的最大数
    val numbers = 100000000         // 在 [0, rangeMaxNumber] 生成 numbers 个不重复的整数

    launch()

    def launch(): Unit = {
        val system:ActorSystem = ActorSystemFactory.newInstance()
        val bigfileSortActor = system.actorOf(Props(new BigFileSortActor(numbers)))
        val checkNumberActor = system.actorOf(Props(new CheckUnduplicatedNumbersActor(numbers, bigfileSortActor)), name="checkNumberActor")
        val numGenTask = new NumberGeneratorTask(numbers, 0, rangeMaxNumber, checkNumberActor)

        val pool = new ForkJoinPool()
        pool.execute(numGenTask)
        pool.shutdown;
        pool.awaitTermination(1024, TimeUnit.SECONDS);
        pool.shutdownNow
    }

}
