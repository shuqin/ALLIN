package cc.lovesq.study.testcase

import cc.lovesq.study.testcase.qa.DetailTestCases
import cc.lovesq.study.testcase.qa.SearchTestCases

class ClientTest {

    def static main(args) {

        CaseExecutor caseExecutor = new CaseExecutor()

        caseExecutor.invokeAllCases(new SearchTestCases())
        caseExecutor.invokeAllCases(new DetailTestCases())

    }
}
