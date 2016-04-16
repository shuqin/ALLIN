package scalastudy.basic

/**
 * Created by lovesqcc on 16-3-18.
 */
object Power2 {

  def main(args: Array[String]): Unit = {
    test()
  }

  def power2(n:Int): BigInt = {
    var res:BigInt = 1
    for (i <- 1 to n) {
      res *= 2
    }
    return res
  }

  def power2_rec(n:Int): BigInt = {
    if (n == 0) return 1
    if (n == 1) return 2

    val part = power2_rec(n/2)
    if (n % 2 == 0) {
      return  part * part
    }
    else {
      return  part * part * 2
    }
  }

  def test() = {
    val testNum = List(0,1,2,3,32, 512, 768, 1023, 1024)
    for (num <- testNum) {
      val v1 = power2(num)
      val v2 = power2_rec(num)
      val stand_v = math.pow(2, num)
      assert( v1 == v2)
      if (num < 1024) {
        assert( v1 == stand_v)
      }
      println("2^"+num+"="+v1)
    }
    println("test passed")
  }

}

