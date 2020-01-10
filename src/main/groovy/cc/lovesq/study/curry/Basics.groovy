package cc.lovesq.study.curry

class Basics {

    static void main(args) {
        def closure = { x,y -> x * x +y }
        def square = closure.rcurry(0)

        println((1..5).collect { square(it) })

        def linear = closure.curry(1)
        println((1..5).collect { linear(it) })

    }
}
