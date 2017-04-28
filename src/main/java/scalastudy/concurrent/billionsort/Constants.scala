package scalastudy.concurrent.billionsort

import scalastudy.utils.PathConstants

/**
  * Created by shuqin on 17/4/27.
  */
object Constants {

  // 生成的整数中不超过的最大数
  val rangeMaxNumber = 100000000

  // 在 [0, rangeMaxNumber] 生成 numbers 个不重复的整数
  val numbers = 10000000

  // 每次生成不超过 threshold 个不重复的整数数组;　
  // 该值不能过小, 否则会因递归层次过深导致内存不足.
  val threshold = numbers / 10

  // 存储生成的不重复整数
  val filename = PathConstants.projPath + s"/data/${numbers}.txt"

  // ForkJoin 池终止前的等待时间
  val poolWaitSecs = 15

  // Debug 选项
  val debug = false

}
