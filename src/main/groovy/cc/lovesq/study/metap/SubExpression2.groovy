package cc.lovesq.study.metap

import groovy.util.logging.Log

@Log
class SubExpression2 extends Expression {

    static void main(args) {
        def exp = new SubExpression2(field: "id", op:"=", value:111)

        SubExpression2.metaClass.invokeMethod = { String name, margs ->
            log.info("enter method=$name, args=$margs")
            //println "enter method=$name, args=$args" // can't call this, because println call will be intercepted to this method
            //match(args) can't call this, because match call will be intercepted to this method

            def result = SubExpression2.metaClass.getMetaMethod(name)?.invoke(delegate, margs)
            log.info("exit method=$name, args=$margs")

            result
        }

        println exp.match([id: 123])
        println exp.match([id: 111])
        //println exp.nonexist()
    }
}
