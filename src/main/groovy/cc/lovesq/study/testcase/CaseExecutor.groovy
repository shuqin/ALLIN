package cc.lovesq.study.testcase

import cc.lovesq.study.testcase.qa.DetailTestCases
import cc.lovesq.study.testcase.qa.SearchTestCases

class CaseExecutor extends GroovyTest {

    def static main(args) {

        GroovyTest groovyTest = new GroovyTest()

        groovyTest.invokeAllCases(new SearchTestCases())
        groovyTest.invokeAllCases(new DetailTestCases())

    }
}
