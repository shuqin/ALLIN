package cc.lovesq.study.testcase.qa

import cc.lovesq.study.testcase.Case

class DetailTestCases {

    def url = 'http://10.9.150.169:7001'
    def path = '/trade_detail/orderInfo/byOrderNo'

    @Case
    def get() {
        return [
           'param': ['kdtId': 55, 'orderNo': 'E20180507200552032000001', 'app':'service-test', 'bizGroup': 'trade'],
           'validations': { data ->
               data.mainOrderInfo.orderNo == 'E20180507200552032000001'
           }
        ]
    }

}


