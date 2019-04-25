package zzz.study.expression

import org.junit.Test
import spock.lang.Specification
import zzz.study.patterns.composite.expression.CombinedExpression
import zzz.study.patterns.composite.expression.SingleExpression
import zzz.study.patterns.composite.expression.WholeExpressions

class ExpressionTest extends Specification {

    @Test
    def "testOrderStateExpression"() {
        expect:
        SingleExpression singleExpression = SingleExpression.getInstance(singleOrderStateExpression)
        singleExpression.getResult(["state":value]) == result

        where:
        singleOrderStateExpression  | value | result
        '{"cond": {"field": "state", "op":"eq", "value":5}, "result":"待发货"}' | 5 | '待发货'
        '{"cond": {"field": "state", "op":"eq", "value":6}, "result":"已发货"}' | 6 | '已发货'
        '{"cond": {"field": "state", "op":"eq", "value":99}, "result":"已关闭"}' | 99 | '已关闭'
    }

    @Test
    def "testOrderStateCombinedExpression"() {
        expect:
        String combinedOrderStateExpress = '''
            {"conditions": [{"field": "activity_type", "op":"eq", "value":13},{"field": "state", "op":"eq", "value":5}, {"field": "tcExtra", "op":"isnull"}], "result":"待开奖"} 
                '''
        CombinedExpression combinedExpression = CombinedExpression.getInstance(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":5, "activity_type":13]) == "待开奖"

    }

    @Test
    def "testOrderStateCombinedExpression2"() {
        expect:
        String combinedOrderStateExpress = '''
            {"conditions": [{"field": "activity_type", "op":"eq", "value":13},{"field": "state", "op":"eq", "value":5}, 
                      {"field": "tcExtra", "op":"notcontains", "value":"LOTTERY"}], "result":"待开奖"} 
                '''
        CombinedExpression combinedExpression = CombinedExpression.getInstance(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":5, "activity_type":13, "tcExtra":[:]]) == "待开奖"
    }

    @Test
    def "testOrderStateCombinedExpression3"() {
        expect:
        String combinedOrderStateExpress = '''
            {"conditions": [{"field": "activity_type", "op":"eq", "value":13},{"field": "state", "op":"eq", "value":50}, 
                      {"field": "tcExtra", "op":"get", "key":"EXT_ORDER_STATUS", "value":"40"}], "result":"待开奖"} 
                '''
        CombinedExpression combinedExpression = CombinedExpression.getInstance(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":50, "activity_type":13, "tcExtra":['EXT_ORDER_STATUS':'40']]) == "待开奖"
    }

    @Test
    def "testWholeExpressions"() {
       expect:
       String wholeExpressionStr = '''
            [{"cond": {"field": "state", "op":"eq", "value":5}, "result":"待发货"},
             {"conditions": [{"field": "activity_type", "op":"eq", "value":13},{"field": "state", "op":"eq", "value":50}], "result":"待开奖"}]
                '''

       WholeExpressions wholeExpressions = WholeExpressions.getInstance(wholeExpressionStr)
       wholeExpressions.getResult(["state":5]) == "待发货"
       wholeExpressions.getResult(["state":50, "activity_type":13]) == "待开奖"
       wholeExpressions.getResult(["state":99]) == ""

    }
}
