package scalastudy.concurrent.forkjoin

import java.util.concurrent.{TimeUnit, ForkJoinPool}

import akka.actor.{Props, ActorSystem}

import scalastudy.concurrent.actors.{BigFileSortActor, CheckUnduplicatedNumbersActor, StatWordActor}
import scalastudy.concurrent.config.ActorSystemFactory

/**
  * Created by shuqin on 16/5/18.
  */
object BillionNumberSort extends App {

    val numbers = 10000000    // 在 [0, 2^31-1] 生成 numbers 个不重复的整数

    launch()

    def launch(): Unit = {
        val system:ActorSystem = ActorSystemFactory.newInstance()
        val bigfileSortActor = system.actorOf(Props(new BigFileSortActor(numbers)))
        val checkNumberActor = system.actorOf(Props(new CheckUnduplicatedNumbersActor(numbers, bigfileSortActor)), name="checkNumberActor")
        val numGenTask = new NumberGeneratorTask(numbers, 0, Integer.MAX_VALUE, checkNumberActor)

        val pool = new ForkJoinPool()
        pool.execute(numGenTask)
        pool.shutdown;
        pool.awaitTermination(420, TimeUnit.SECONDS);
        pool.shutdownNow
    }

}
