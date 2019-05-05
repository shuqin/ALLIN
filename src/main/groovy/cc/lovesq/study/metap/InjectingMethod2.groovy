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
    }
}
