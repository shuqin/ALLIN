package cc.lovesq.study.metap

class InjectingMethod2 {

    static void main(args) {

        Map.metaClass.readVal = { path ->
            if (delegate?.isEmpty || !path) { return  null }
            def paths = path.split("\\.")
            def result = delegate
            paths.each { subpath ->
                result = result?.get(subpath)
            }
            result
        }

        def skills = [id:123, name:'qin', 'skills': ['programming':'good', 'writing': 'good', 'expression':'not very good']]
        println( skills.readVal('name') + " can do:\n" +
                ['programming', 'writing', 'expression', 'dance'] .collect { "skills.$it" } .collect { "\t$it ${skills.readVal(it)}" }.join('\n') )

        Map.metaClass {

            flatMap = { ->
                def finalResult = [:]
                delegate.each { key, value ->
                    if (value instanceof Map) {
                        def innerMap = [:]
                        value.each { k, v ->
                            innerMap[key+'.'+k] = v
                        }
                        finalResult.putAll(delegate.flatMap(innerMap))
                    }
                    else {
                        finalResult[key] = value
                    }
                }
                finalResult
            }

            'static' {
                pretty = {
                    "[" + delegate.collect { it }.join(",") + "]"
                }
            }

        }

        println "pretty print: " + Map.pretty(skills)
        println 'flatMap:' + skills.flatMap()

    }
}
