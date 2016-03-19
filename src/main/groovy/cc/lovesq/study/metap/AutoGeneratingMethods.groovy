package cc.lovesq.study.metap

class AutoGeneratingMethods {

    def can(skill) {
        return { ->
            println "i can $skill"
        }
    }

    def canAdvanced(skill) {
        AutoGeneratingMethods.metaClass."$skill" = { ->
            println "i can $skill advanced."
        }
    }

    static void main(args) {
        def agm = new AutoGeneratingMethods()
        def skills = ['swim', 'piano', 'writing']
        skills.each {
            agm.can(it)()
        }
        println("using closure: class methods: " + AutoGeneratingMethods.metaClass.methods.collect { it.name })
        println("using closure: object methods: " + agm.metaClass.methods.collect { it.name })

        def agm2 = new AutoGeneratingMethods()
        def newNewSkills = ['rocking', 'travel', 'climbing']
        newNewSkills.each {
            def thisSkill = it
            agm2.metaClass."$it" = { ->
                println "i can $thisSkill dynamically"
            }
            agm2."$it"()
        }

        println("use object injecting: class methods: " + AutoGeneratingMethods.metaClass.methods.collect { it.name })
        println("use object injecting: object methods: " + agm2.metaClass.methods.collect { it.name })

        def agm3 = new AutoGeneratingMethods()
        def newSkills = ['dance', 'drawing', 'thinking']
        newSkills.each {
            agm3.canAdvanced(it)()
        }

        println("using class method injecting: class methods: " + AutoGeneratingMethods.metaClass.methods.collect { it.name })
        println("using class method injecting: object methods: " + agm3.metaClass.methods.collect { it.name })

    }
}
