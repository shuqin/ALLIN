package cc.lovesq.study.testcase.qa

import cc.lovesq.study.testcase.Case

class SearchTestCases {

    def url = 'http://10.9.146.160:7001'
    def path = '/trade-manage/order/search'

    @Case
    def getOrderNoSearchCase() {
        [
                'name' : 'testSearchOrderNo',
                'param': ['kdtId': 55, 'orderNo': 'E20180507200552032000001', 'sourceName': 'service-test'],
                'check': { data ->
                    data.list.each {
                        order ->
                            order.orderNo == 'E20180507200552032000001'
                    }
                }
        ]
    }

}


