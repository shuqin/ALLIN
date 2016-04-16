package scalastudy.basic

import scala.math.pow;

/**
 * Created by lovesqcc on 16-4-16.
 */
object CurryDemo extends App {

  launch()

  def launch(): Unit = {

    val listNum = 10
    val alist = (1 to listNum).toList
    val listSum = polynomialSum(alist)(_)
    println(listSum)

    for (i <- 1 to 3) {
      println(listSum(i))
    }
  }

  def polynomialSum(list: List[Int])(m: Int): Long = {
      return list.map(pow(_,m)).sum.asInstanceOf[Long];
  }

}
