package scalastudy.concurrent.billionsort

import java.util.concurrent.RecursiveAction

import akka.actor.ActorRef
import zzz.study.algorithm.select.RandomSelector

/**
  *  Created by shuqin on 16/5/19.
  *
  * 在 [start, end] 选出 num 个不重复的整数
  *
  */
class NumberGeneratorTask(num:Int, start:Int, end:Int, checkNumberActor: ActorRef)  extends RecursiveAction {

    // 每次生成不超过 threshold 个不重复的整数数组;　
    // 该值不能过小, 否则会因递归层次过深导致内存不足.
    private val threshold = 500

    override def compute(): Unit = {

        // println("Select: " + num  + " unduplicated numbers from [" + start + " " + end + ")");

        // TODO bug if num == numbers that all need sort, cannot generate numbers
        if (num <= threshold) {

            if (num > end - start+1) {
                checkNumberActor ! start.to(end).toList
            }
            else {
                val randInts = RandomSelector.selectMDisorderedRandInts2(num, end-start+1)
                checkNumberActor ! randInts.map(i=>i+start).toList
            }
        }
        else {
            val middle = start/2 + end/2
            val leftTask = new NumberGeneratorTask(num/2, start, middle, checkNumberActor)
            val rightTask = new NumberGeneratorTask((num+1)/2, middle+1, end, checkNumberActor)
            //println("Left: [" + start + "-" + middle + "," + num/2 + "]")
            //println("Right: [" + (middle+1) + "-" + end + "," + (num+1)/2 + "]")

            leftTask.fork
            rightTask.fork
            leftTask.join
            rightTask.join
            checkNumberActor ! (start, end)
        }
    }

}
