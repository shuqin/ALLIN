package cc.lovesq.study.testcase

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static groovyx.net.http.Method.POST

class GroovyTest {

    static Logger log = LoggerFactory.getLogger(GroovyTest.class)

    def kdtId = 55

    def invokeAllTests() {

        Map invokeResultMap = [:]
        Object obj = this
        obj.getClass().getDeclaredMethods().findAll {

            // 识别测试方法： 带有 @TestMethod 或者 以test开头
            it.getAnnotation(TestMethod.class) != null || it.name.startsWith("test")

        }.each {

            try {
                log.info("Enter Test Method : {}", it.name)
                invokeResultMap[it.name] = it.invoke(obj, null)
            } catch (Throwable e) {
                invokeResultMap[it.name] = "failed"
            } finally {
                log.info("Exit Test Method : {}", it.name)
            }

        }
        return invokeResultMap
    }

    def invokeAllCases(testCase) {
        Object tc = testCase
        tc.getClass().getDeclaredMethods().findAll {

            // 识别测试用例： 带有 @Case
            it.getAnnotation(Case.class) != null

        }.each {
            it ->
                def caseInfo = it.invoke(tc, null)
                exec(caseInfo['name'], tc.url, tc.path, caseInfo['param'], caseInfo['check'])
        }
    }

    def exec(name, url, path, param, check) {

        def http = new HTTPBuilder(url)

        http.request(POST) {
            uri.path = path
            requestContentType = ContentType.JSON
            body = param

            response.success = { resp, json ->
                def data = json.data.data
                check(data)
            }
            response.failure = { resp ->
                println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
            }
        }
    }


}
