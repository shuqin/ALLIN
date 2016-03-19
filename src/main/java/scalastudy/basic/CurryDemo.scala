package scalastudy.basic

import scala.math.pow
import scalastudy.utils.PathConstants
import scalastudy.utils.DefaultFileUtil._

/**
 * Created by lovesqcc on 16-4-16.
 */
object CurryDemo extends App {

  launch()

  def launch(): Unit = {

    val listNum = 10
    val alist = (1 to listNum).toList

    for (i <- 1 to 3) {
      val listPolySum = polynomialSum(i)(_)
      val sum = listPolySum(alist)
      assert(sum == polynomialSum2(i, listNum))
      assert(sum == polynomialSumNotGood(alist)(i))
      println("sum: " + sum)
    }
    println("test passed.")

    val filename = PathConstants.scalaSrcPath + "/basic/CurryDemo.scala"
    val fileContentHandler = handleFile(readFile(_))(_)
    val findInFileFunc = fileContentHandler(findInFile)(_)
    println(findInFileFunc(filename))

    val countInFileFunc = fileContentHandler(countInFile)(_)
    println("Non Empty Lines: " + countInFileFunc(filename))

    val result = handleFiles(fetchAllFiles)((file:String) => file.endsWith("scala"))( List((s:String) => readFileLines(s)))((liststr: List[Any]) => liststr.mkString)(PathConstants.srcPath)
    println(result)
  }

  /* calc 1^m + 2^m + ... + n^m */
  def polynomialSum2(m:Int, n:Int):Long = {
    return 1.to(n).toList.map(pow(_,m)).sum.asInstanceOf[Long];
  }

  /* calc 1^m + 2^m + ... + n^m */
  def polynomialSum(m: Int)(list: List[Int]): Long = {
    return list.map(pow(_,m)).sum.asInstanceOf[Long];
  }

  /* calc 1^m + 2^m + ... + n^m */
  def polynomialSumNotGood(list: List[Int])(m:Int):Long = {
    return list.map(pow(_,m)).sum.asInstanceOf[Long];
  }

}
