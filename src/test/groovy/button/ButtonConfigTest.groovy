package button

import com.alibaba.fastjson.JSON
import org.junit.Test
import shared.conf.GlobalConfig
import shared.script.ScriptExecutor
import spock.lang.Specification
import spock.lang.Unroll
import zzz.study.patterns.composite.button.BaseCondition
import zzz.study.patterns.composite.button.CondOp
import zzz.study.patterns.composite.button.strategy.ConditionParserStrategy
import zzz.study.patterns.composite.button.strategy.ParserStrategyFactory

class ButtonConfigTest extends Specification {

    ScriptExecutor scriptExecutor = new ScriptExecutor()
    GlobalConfig config = new GlobalConfig()

    def setup() {
        scriptExecutor.globalConfig = config
        scriptExecutor.init()
    }

    @Test
    def "testComplexConfigByGroovy"() {
        when:
        Domain domain = new Domain()
        domain.state = 20
        domain.orderNo = 'E0001'
        domain.orderType = 0

        then:
        testCond(domain)
    }

    @Test
    def "testConditions"() {
        expect:
        ConditionParserStrategy parserStrategy = new ParserStrategyFactory().getParser("json")
        def singleCondJson = '{"cond":{"field": "activity_type", "op":"eq", "value": 13}, "result": true}'
        def singleButtonCondition = parserStrategy.parseSingle(singleCondJson)
        def valueMap = ["activity_type": 13]
        singleButtonCondition.satisfiedBy(valueMap) == true
        singleButtonCondition.getResult() == true

        def multiCondJson = '{"conditions": [{"field": "activity_type", "op":"eq", "value": 13}, {"field": "feedback", "op":"gt", "value": 201}], "result": false}'
        def multiButtonCondition = parserStrategy.parseMulti(multiCondJson)
        def valueMap2 = ["activity_type": 13, "feedback": 250]
        multiButtonCondition.satisfiedBy(valueMap2) == true
        multiButtonCondition.getResult() == false

        def buttonConfigJson = '{"buttonRules": [{"cond":{"field": "activity_type", "op":"eq", "value": 63}, "result": false}, {"cond":{"field": "order_type", "op":"eq", "value": 75}, "result": false}, ' +
                '{"conditions": [{"field": "state", "op":"neq", "value": 10}, {"field": "order_type", "op":"eq", "value": 0}, {"field": "activity_type", "op":"neq", "value": 13}], "result": true}], "defaultResult": false}'
        def combinedCondition = parserStrategy.parse(buttonConfigJson)
        def giftValueMap = ["activity_type": 63]
        def giftResult = combinedCondition.satisfiedBy(giftValueMap)
        assert giftResult == false

        def knowledgeValueMap = ["activity_type": 0, "order_type": 75]
        def knowledgeResult = combinedCondition.satisfiedBy(knowledgeValueMap)
        assert knowledgeResult == false

        def periodValueMap = ["state": 20, "order_type": 0, "activity_type": 0]
        def periodResult = combinedCondition.satisfiedBy(periodValueMap)
        assert periodResult == true

        def complexValueMap = ["state": 20, "order_type": 0, "activity_type": 13]
        def complexResult = combinedCondition.satisfiedBy(complexValueMap)
        assert complexResult == false
    }

    @Unroll
    @Test
    def "testBaseCondition"() {
        expect:
        new BaseCondition(field, op, value).test(valueMap) == result

        where:
        field      | op         | value      | valueMap          | result
        'feedback' | CondOp.eq  | 201        | ['feedback': 201] | true
        'feedback' | CondOp.in  | [201, 250] | ['feedback': 201] | true
        'feedback' | CondOp.gt  | 201        | ['feedback': 202] | true
        'feedback' | CondOp.gte | 201        | ['feedback': 202] | true
        'feedback' | CondOp.lt  | 201        | ['feedback': 250] | false
        'feedback' | CondOp.lte | 201        | ['feedback': 250] | false
    }

    void testCond(domain) {
        Binding binding = new Binding()
        binding.setVariable("domain", domain)
        def someButtonLogicFromApollo = 'domain.orderType == 10 && domain.state != null && domain.state != 20'
        println "domain = " + JSON.toJSONString(domain)

        (0..100).each {
            long start = System.currentTimeMillis()
            println "someButtonLogicFromApollo ? " +
                    scriptExecutor.exec(someButtonLogicFromApollo, binding)
            long end = System.currentTimeMillis()
            println "costs: " + (end - start) + " ms"
        }

    }
}

class Domain {

    /** 订单编号 */
    String orderNo

    /** 订单状态 */
    Integer state

    /** 订单类型 */
    Integer orderType

}
