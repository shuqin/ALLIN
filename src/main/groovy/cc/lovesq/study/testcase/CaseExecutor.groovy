package cc.lovesq.study.testcase

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static groovyx.net.http.Method.POST

class CaseExecutor {

    static Logger log = LoggerFactory.getLogger(CaseExecutor.class)

    def invokeAllCases(testCase) {
        Object tc = testCase
        tc.getClass().getDeclaredMethods().findAll {

            // 识别测试用例： 带有 @Case
            it.getAnnotation(Case.class) != null

        }.each {
            it ->
                def caseInfo = it.invoke(tc, null)
                def result = exec(caseInfo['name'], tc.url, tc.path, caseInfo['param'], caseInfo['check'])
                println("case=${caseInfo['name']}, result=${result}")
        }
    }

    def exec(name, url, path, param, check) {

        def http = new HTTPBuilder(url)

        def result

        http.request(POST) {
            uri.path = path
            requestContentType = ContentType.JSON
            body = param

            response.success = { resp, json ->
                def data = json.data.data
                try {
                    log.info("Enter Test Case : {}", name)
                    check(data)
                    result = "success"
                } catch (Throwable e) {
                    result = "failed"
                } finally {
                    log.info("Exit Test Case : {}", name)
                }
            }
            response.failure = { resp ->
                println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
                def errorInfo = """
                    Call error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}
                    Name: ${name}
                    Url: ${url}
                    Path: ${path}
                    Param: ${param}
                """
                log.warn(errorInfo)
                result = "failed"
            }
        }
        result
    }
}
