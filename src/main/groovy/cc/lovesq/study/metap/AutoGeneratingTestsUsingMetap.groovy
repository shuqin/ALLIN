package cc.lovesq.study.metap

import com.alibaba.fastjson.JSON
import groovy.util.logging.Log

@Log
class AutoGeneratingTestsUsingMetap {

    def static generateTest(testData) {

        def testMethodName = "test${testData.params.collect { "$it.key = $it.value" }.join('_')}"

        AutoGeneratingTestsUsingMetap.metaClass."$testMethodName" = { tdata ->

            def orderSearchParam = new OrderSearchParam()
            tdata.params.each { pprop, pvalue ->
                orderSearchParam."$pprop" = pvalue
            }
            log.info(JSON.toJSONString(orderSearchParam))
            def result = mockSearch(orderSearchParam)
            assert result.code == 200
            assert result.msg == 'success'
            result.orders.each { order ->
                tdata.validations.each { vdField, vdValue ->
                    assert order."$vdField" == vdValue
                }
            }
            log.info("test passed.")
        }(testData)

        println(AutoGeneratingTestsUsingMetap.metaClass.methods.collect{ it.name })
    }

    static void main(args) {
        AutoGeneratingTestsUsingMetap.generateTest(
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
