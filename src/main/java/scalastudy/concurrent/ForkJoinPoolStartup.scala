package scalastudy.concurrent

import java.util.concurrent.{ForkJoinPool, TimeUnit}

import scalastudy.concurrent.billionsort.NumberGeneratorTask

/**
  * Created by shuqin on 17/4/27.
  */
object ForkJoinPoolStartup {

  def start(entranceTask:NumberGeneratorTask, waitSecs:Int):Unit = {
    val pool = new ForkJoinPool()
    pool.execute(entranceTask)
    pool.shutdown
    pool.awaitTermination(waitSecs, TimeUnit.SECONDS)
    pool.shutdownNow
    assert( pool.isTerminated == true )
  }

}
