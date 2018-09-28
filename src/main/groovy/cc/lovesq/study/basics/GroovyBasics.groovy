package cc.lovesq.study.basics

import groovy.io.FileType

class GroovyBasics {

    def static swim() {
        println "swim"
    }

    def static football() {
        println "football"
    }

    static void main(args) {

        // dynamic definition

        def chameleon = 0
        println "i am a ${chameleon.getClass().name}"

        chameleon = 'haha changed'
        println "now i am ${chameleon}"

        chameleon = { a1, a2, op -> op(a1,a2) }
        println "now i have changed to ${chameleon.getClass().name}"
        println "power(2,10) = ${chameleon(2,10, {bg,p->Math.pow(bg,p)})}"

        // dynamic function call

        ['swim', 'football'].each {
            "${it}"()
        }

        // string about

        def name = 'qin'
        def intro = "i am $name"
        def intro2 = 'i am $name'
        println "intro=$intro, size = ${intro.length()} intro2=$intro2 size=${intro2.length()}"

        def multilines = '''There will be one day when
            i am proud of who i am.
            Because i believe my heart.'''
        println multilines

        println multilines =~ "\\w+\\s+" ? 'matched' : 'not matched'
        println multilines ==~ "\\w+\\s+" ? 'matched' : 'not matched'

        def matched = multilines =~ /\w+\s+/
        matched.each {
            println "matched: $it"
        }

        def isAllMatched = multilines ==~ /(?m)^(([a-zA-z]+\s+)+([a-zA-z]+[.\n]+)\s*)+$/
        println isAllMatched ? 'All matched' : 'not matched'


        def matchedCaptured = multilines =~ /(?:[a-zA-z]+\s+)+(?:[a-zA-z]+[.\n]+)/
        matchedCaptured.each {
            println "matchedCaptured: $it"
        }

        // map

        def map = ["me":["name": 'qin', "age": 28], "lover":["name": 'ni', "age": 25]]
        map.each {
            key, value -> println(key+"="+value)
        }
        println map['me']
        println map.lover
        println map.getOrDefault("nonexist", [:])

        println map.collect { it.value['name'] }
        println map.findAll { it.value.age <=25 }

        // list

        def alist = [1,3,5,7,9]
        alist.each {
            println(it)
        }

        println "first: " + alist.get(0)
        println "second: " + alist[1]
        println alist[2..4]
        println alist[-1]

        alist.eachWithIndex {
            int entry, int i ->
                println "index=$i, item=$entry"
        }

        println alist.findAll {
            it % 3 == 0
        }

        println alist.collect { it * it }

        println '[' + alist.join(",") + ']'

        println alist.inject(1) { n1, n2 -> n1 * n2 }
        println ([[1,2,4], [1,3,9], [1,4,16]].flatten())
        println ([[[1,5,10], [1,6,12]], []].flatten())

        (1..10).each { println(it) }

        (1..10).groupBy { it % 3 == 0 } .each {
            key, value -> println(key.toString()+"="+value)
        }

        // object

        def persons = [new Person(["name": 'qin', "age": 28, "address": new Address(["detail": "Hangzhou"])]), new Person(["name": 'ni', "age": 25])]
        println persons.collect { "${it.name} live in ${it.address?.getDetail()}" }
        println persons.find { it.age >=28 }.name

        persons[0].class.declaredMethods.findAll {
            it.parameterCount == 0
        }.each {
            println "method = ${it.name}, callValue = ${persons[0]."${it.name}"()}"
        }

        // file

        File file = new File("README.md")
        println file.text
        file.eachLine("UTF-8") {
            println it
        }

        println file.readLines()

        new File("/tmp/result.txt").withPrintWriter { printWriter ->
            printWriter.println('The first content of file')
        }

        def printWriter = new File("/tmp/result2.txt").newPrintWriter()
        printWriter.write('The first content of file')
        printWriter.flush()
        printWriter.close()

        File path = new File(System.getProperty("user.dir"))
        println "absolutePath: ${path.getCanonicalPath()}, isDirectory:  ${path.isDirectory()}"

        path.eachFileRecurse(FileType.FILES) {
            def filename = it.name
            if (filename =~ ~/.*\.groovy$/) {
                println filename
            }
        }

        path.eachFileMatch(~/.*\..*/) {
            println it.name
        }

        path.eachDirRecurse {
            def filename = it.canonicalPath
            if (filename =~ ~/.*groovy$/) {
                println filename
            }
        }


    }
}

class Person {
    def name
    def age
    def address
}

class Address {
    def detail
}
