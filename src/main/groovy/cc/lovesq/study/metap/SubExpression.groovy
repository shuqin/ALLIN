package cc.lovesq.study.metap

import groovy.util.logging.Log

@Log
class SubExpression extends Expression implements GroovyInterceptable {

    def invokeMethod(String name, args) {
        log.info("enter method=$name, args=$args")
        //println "enter method=$name, args=$args"  can't call this, because println call will be intercepted to this method
        //match(args) can't call this, because match call will be intercepted to this method

        def result = this.metaClass.getMetaMethod(name)?.invoke(this, args)
        log.info("exit method=$name, args=$args")

        result
    }

    static void main(args) {
        def exp = new SubExpression(field: "id", op:"=", value:111)
        println exp.match([id: 123])
        println exp.match([id: 111])
        println exp.nonexist()

    }
}

