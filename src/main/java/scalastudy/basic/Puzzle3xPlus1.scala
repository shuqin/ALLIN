package scalastudy.basic

/**
 * Created by shuqin on 17/3/19.
 *
 * 3x+1 谜题, 演示链式调用
 */
object Puzzle3xPlus1 extends App {

    println(new P3xPlus1Number(78).do3xplus1().do3xplus1().do3xplus1())
    println(new P3xPlus1Number(78).solve())
    1 to 1000 map { new P3xPlus1Number(_).solve() } map { t => (t._1, t._2) } foreach { println }
}

class P3xPlus1Number(init:Int) {

    var innerNumber = init

    def do3xplus1(): P3xPlus1Number = {
        innerNumber = if (innerNumber%2==1) { innerNumber*3+1 } else { innerNumber/2 }
        this
    }

    def solve(): (Int, Int, List[Int]) = {
        var sol = (init, 0, List[Int]())
        while (innerNumber != 1) {
            sol = (init, sol._2 + 1, innerNumber::sol._3)
            do3xplus1()
        }
        (init, sol._2 + 1, (innerNumber::sol._3).reverse)
    }

    override def toString():String = {
        "P3xPlus1Number["+innerNumber+"]"
    }
}
