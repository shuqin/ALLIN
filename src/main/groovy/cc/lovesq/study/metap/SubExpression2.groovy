package cc.lovesq.study.metap

import groovy.util.logging.Log

@Log
class SubExpression2 extends Expression {

    static void main(args) {

        // must be the first line
        SubExpression2.metaClass.invokeMethod = { String name, margs ->
            log.info("enter method=$name, args=$margs")

            def result = SubExpression2.metaClass.getMetaMethod(name)?.invoke(delegate, margs)
            log.info("exit method=$name, args=$margs")

            result
        }

        def exp = new SubExpression2(field: "id", op:"=", value:111)

        println exp.match([id: 123])
        println exp.match([id: 111])
        println exp.nonexist()
    }
}
