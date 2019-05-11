package cc.lovesq.study.metap

import com.alibaba.fastjson.JSON
import groovy.util.logging.Log

@Log
class AutoGeneratingTestsPlain {

    def static generateTest(testCase) {

        def orderSearchParam = new OrderSearchParam()
        testCase.params.each { pprop, pvalue ->
            orderSearchParam."$pprop" = pvalue
        }
        log.info(JSON.toJSONString(orderSearchParam))
        def result = mockSearch(orderSearchParam)
        assert result.code == 200
        assert result.msg == 'success'
        result.orders.each { order ->
            testCase.validations.each { vdField, vdValue ->
                assert order."$vdField" == vdValue
            }
        }
        log.info("test passed.")
    }

    static void main(args) {
        AutoGeneratingTestsPlain.generateTest(
                [
                    params: [
                        'orderTypeDesc': ['NORMAL'],
                        'recName': 'qin'
                    ],
                    validations: [
                            'order_type': 0,
                            'rec_name': 'qin'
                    ]
                ]
        )
    }

    def static mockSearch(orderSearchParam) {
        def results = new Expando(msg: 'success' , code: 200)
        results.orders = (1..20).collect {
            new Expando(order_type:0 , rec_name: 'qin')
        }
        results
    }

}
