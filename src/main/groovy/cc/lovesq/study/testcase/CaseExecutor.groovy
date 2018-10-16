package cc.lovesq.study.testcase

import cc.lovesq.study.testcase.qa.DetailTestCases
import cc.lovesq.study.testcase.qa.SearchTestCases
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*

class CaseExecutor {

    def static exec(name, url, path, param, validations) {

        def http = new HTTPBuilder(url)

        http.request(POST) {
            uri.path = path
            requestContentType = ContentType.JSON
            body = param

            response.success = { resp, json ->
                def data = json.data.data
                validations(data)
            }
            response.failure = { resp -> println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}" }
        }
    }

    def static main(args) {

        def detailCases = new DetailTestCases()
        Object dc = detailCases
        dc.getClass().getDeclaredMethods().findAll {

            // 识别测试用例： 带有 @Case
            it.getAnnotation(Case.class) != null

        }.each {
            it ->
                def caseInfo = it.invoke(dc, null)
                exec("testOrderDetail", detailCases.url, detailCases.path, caseInfo['param'], caseInfo['validations'])
        }

        def searchCases = new SearchTestCases()
        Object sc = searchCases
        sc.getClass().getDeclaredMethods().findAll {

            // 识别测试用例： 带有 @Case
            it.getAnnotation(Case.class) != null

        }.each {
            it ->
                def caseInfo = it.invoke(sc, null)
                exec("testOrderSearch", searchCases.url, searchCases.path, caseInfo['param'], caseInfo['validations'])
        }

    }
}
