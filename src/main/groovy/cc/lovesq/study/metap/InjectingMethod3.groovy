package cc.lovesq.study.metap

class InjectingMethod3 {

    static void main(args) {

        Map.metaClass {

            flatMap = { ->
                def finalResult = [:]
                delegate.each { key, value ->
                    if (value instanceof Map) {
                        def innerMap = [:]
                        value.each { k, v ->
                            innerMap[key+'.'+k] = v
                        }
                        finalResult.putAll(innerMap)
                    }
                    else {
                        finalResult[key] = value
                    }
                }
                finalResult
            }

            methodMissing = { name, margs ->
                "Unknown method=$name, args=$margs"
            }

            'static' {
                pretty = { map ->
                    "[" + map.collect { it }.join(",") + "]"
                }
            }

        }

        def skills = [id:123, name:'qin', 'skills': ['programming':'good', 'writing': 'good', 'expression':'not very good']]

        println "pretty print: " + Map.pretty(skills)
        println 'flatMap:' + skills.flatMap()
        println 'nonexist: ' + skills.nonexist()

    }
}
