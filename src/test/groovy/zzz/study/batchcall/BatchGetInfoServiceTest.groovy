package zzz.study.batchcall

import com.alibaba.fastjson.JSONObject
import org.junit.Test
import shared.multitasks.MultiTaskExecutor
import shared.rpc.BatchGetInfoService
import shared.rpc.HttpClient
import shared.rpc.batchcall.BatchHttpRestParam
import spock.lang.Specification

import java.util.function.Function

/**
 * Created by shuqin on 18/3/12.
 */
class BatchGetInfoServiceTest extends Specification {

    BatchGetInfoService batchGetInfoService = new BatchGetInfoService()
    HttpClient httpClient = Mock(HttpClient)
    MultiTaskExecutor multiTaskExecutor = Mock(MultiTaskExecutor)

    def setup() {
        batchGetInfoService.httpClient = httpClient
        batchGetInfoService.multiTaskExecutor = multiTaskExecutor
    }


    @Test
    def "testBatchGetInfo"() {
        given:
        def resp = JSONObject.parse("{\"result\":true,\"code\":0,\"data\":{\"code\":200,\"data\":[{\"setPassword\":false,\"country\":\"\",\"updatedTime\":1514384606000,\"gender\":0,\"city\":\"\",\"nickName\":\"小火龙????\",\"groupId\":1,\"mobileUserInfo\":{\"setPassword\":false,\"groupId\":1,\"mobile\":\"18757555242\",\"userId\":84863673,\"platform\":false,\"password\":\"\",\"countryCode\":\"+86\",\"passwordSalt\":\"\"},\"mobile\":true,\"county\":\"\",\"sign\":\"\",\"avatar\":\"http://wx.qlogo.cn/mmopen/KydxAIB52xnSLohFJZicNwUtb3Ql9pAowMFDbLOMlUFIpVee1LeY8hFsia03Fhl3ibQ8kSOicMSqZe9YMlZCpx1QrA/0\",\"source\":7,\"userId\":84863673,\"platform\":true,\"regIp\":\"0:0:0:0\",\"platformUserInfoList\":[{\"country\":\"\",\"city\":\"\",\"groupId\":1,\"platformType\":1805,\"mobile\":true,\"county\":\"\",\"telephone\":\"\",\"source\":0,\"userId\":84863673,\"platform\":true,\"regIp\":\"\",\"province\":\"\",\"platformFansId\":2352928433,\"extra\":\"\"},{\"country\":\"\",\"city\":\"\",\"groupId\":1,\"platformType\":9,\"mobile\":true,\"county\":\"\",\"telephone\":\"\",\"source\":0,\"userId\":84863673,\"platform\":true,\"regIp\":\"\",\"province\":\"\",\"platformFansId\":407197440,\"extra\":\"\"},{\"country\":\"\",\"city\":\"\",\"groupId\":1,\"platformType\":6227,\"mobile\":true,\"county\":\"\",\"telephone\":\"\",\"source\":0,\"userId\":84863673,\"platform\":true,\"regIp\":\"\",\"province\":\"\",\"platformFansId\":4294620137,\"extra\":\"\"},{\"country\":\"\",\"city\":\"\",\"groupId\":1,\"platformType\":10497,\"mobile\":true,\"county\":\"\",\"telephone\":\"\",\"source\":0,\"userId\":84863673,\"platform\":true,\"regIp\":\"\",\"province\":\"\",\"platformFansId\":4903094389,\"extra\":\"\"}],\"password\":\"\",\"province\":\"\",\"createdTime\":1449556247000,\"passwordSalt\":\"\"}],\"success\":true,\"requestId\":\"order-export_1520836753213\",\"count\":1,\"message\":\"successful\"}}")

        when:
        httpClient.query(_,_) >> resp

        then:
        def batchHttpRestParam = new BatchHttpRestParam()
        def dataList = batchGetInfoService.batchGetInfo(batchHttpRestParam)
        dataList.size() == 0

        batchHttpRestParam.setKeys([84863673])
        batchHttpRestParam.setUrl('url')
        batchHttpRestParam.setParamBuilderFunc({ it -> new HashMap<>() } as Function)

        def dataList2 = batchGetInfoService.batchGetInfo(batchHttpRestParam)
        dataList2.size() == 1
        dataList2[0]['userId'] == 84863673
        dataList2[0]['nickName'] == '小火龙????'
    }

    @Test
    def "testBatchgetAllInfo"() {
        given:
        def allInfo = [
                [
                        'userId': 111,
                        'nickName': 'haha'
                ],
                [
                        'userId': 222,
                        'nickName': 'hehe'
                ]
        ]

        when:
        multiTaskExecutor.exec(_,_,_) >> allInfo

        then:
        def batchHttpRestParam = new BatchHttpRestParam()
        def dataList = batchGetInfoService.batchGetInfo(batchHttpRestParam)
        dataList.size() == 0

        batchHttpRestParam.setKeys([84863673])
        batchHttpRestParam.setUrl('url')
        batchHttpRestParam.setParamBuilderFunc({ it -> new HashMap<>() } as Function)

        def dataList2 = batchGetInfoService.batchGetAllInfo(batchHttpRestParam, 30)
        dataList2.size() == 2
        dataList2[0]['userId'] == 111
        dataList2[0]['nickName'] == 'haha'
        dataList2[1]['userId'] == 222
        dataList2[1]['nickName'] == 'hehe'
    }
}
