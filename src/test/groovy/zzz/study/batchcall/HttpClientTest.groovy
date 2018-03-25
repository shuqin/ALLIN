package zzz.study.batchcall

import com.alibaba.fastjson.JSON
import org.apache.http.ProtocolVersion
import org.apache.http.entity.BasicHttpEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.execchain.HttpResponseProxy
import org.apache.http.message.BasicHttpResponse
import org.apache.http.message.BasicStatusLine
import spock.lang.Specification
import zzz.study.tech.batchcall.HttpClient

/**
 * Created by shuqin on 18/3/12.
 */
class HttpClientTest extends Specification {

    HttpClient httpClient = new HttpClient()
    CloseableHttpClient syncHttpClient = Mock(CloseableHttpClient)

    def setup() {
        httpClient.syncHttpClient = syncHttpClient
    }

    def "testHttpClientQuery"() {

        given:
        def statusLine = new BasicStatusLine(new ProtocolVersion("Http", 1, 1), 200, "")
        def resp = new HttpResponseProxy(new BasicHttpResponse(statusLine), null)
        resp.statusCode = 200

        def httpEntity = new BasicHttpEntity()
        def respContent = JSON.toJSONString([
                "code": 200, "message": "success", "total": 1200
        ])
        httpEntity.content = new ByteArrayInputStream(respContent.getBytes("utf-8"))
        resp.entity = httpEntity

        when:
        syncHttpClient.execute(_) >> resp

        then:
        def callResp = httpClient.query("query", "http://127.0.0.1:80/uic/user/info/list")
        callResp.size() == 3
        callResp[field] == value

        where:
        field     | value
        "code"    | 200
        "message" | "success"
        "total"   | 1200

    }
}

