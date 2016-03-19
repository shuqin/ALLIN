package scalastudy.basic

/**
 * Created by lovesqcc on 16-3-19.
 */
package scalastudy

/**
 * Created by lovesqcc on 16-3-18.
 */
object Power2 {

  def main(args: Array[String]): Unit = {
    val res1 = power2(1024)
    val res2 = power2_rec(1024)
    assert(res1 == res2)
    println(res1)
  }

  def power2(n:Int): BigInt = {
    var res:BigInt = 1
    for (i <- 1 to n) {
      res *= 2
    }
    return res
  }

  def power2_rec(n:Int): BigInt = {
    if (n == 1) return 2
    else return power2_rec(n/2) * power2_rec(n/2)
  }

}

