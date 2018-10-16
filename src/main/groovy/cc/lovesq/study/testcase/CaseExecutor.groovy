package cc.lovesq.study.testcase

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*

class CaseExecutor {

    def static exec(name, url, path, param, validations) {

        def http = new HTTPBuilder(url)

        http.request(POST) {
            uri.path = path
            requestContentType = ContentType.JSON
            body = param

            response.success = { resp, json ->
                def data = json.data
                validations(data)
            }
            response.failure = { resp -> println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}" }
        }
    }

    def static main(args) {
        def url = 'http://10.9.150.169:7001'
        def path = '/trade_detail/orderInfo/byOrderNo'
        def param = ['kdtId': 55, 'orderNo': 'E20180507200552032000001', 'app':'service-test', 'bizGroup': 'trade']
        def validations = {
            it.data.mainOrderInfo.orderNo == 'E20180507200552032000001'
        }

        exec("testSearchOldOrder", url, path, param, validations)
    }
}
