package zzz.study.expression

import org.junit.Test
import spock.lang.Specification
import zzz.study.patterns.composite.expression.CombinedExpression
import zzz.study.patterns.composite.expression.SingleExpression
import zzz.study.patterns.composite.expression.WholeExpressions
import zzz.study.patterns.composite.expression.parser.ExrepssionJsonParser

class ExpressionJsonTest extends Specification {

    ExrepssionJsonParser expressionJsonParser = new ExrepssionJsonParser()

    @Test
    def "testOrderStateExpression"() {
        expect:
        SingleExpression singleExpression = expressionJsonParser.parseSingle(singleOrderStateExpression)
        singleExpression.getResult(["state": value]) == result

        where:
        singleOrderStateExpression                                                | value  | result
        '{"cond": {"field": "state", "op":"eq", "value":"PAID"}, "result":"待发货"}' | "PAID" | '待发货'
    }

    @Test
    def "testOrderStateCombinedExpression"() {
        expect:
        String combinedOrderStateExpress = '''
            {"conditions": [{"field": "activity", "op":"eq", "value":"LOTTERY"},{"field": "state", "op":"eq", "value":"PAID"}, {"field": "extra", "op":"isnull"}], "result":"待开奖"} 
                '''
        CombinedExpression combinedExpression = expressionJsonParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state": "PAID", "activity": "LOTTERY"]) == "待开奖"

    }

    @Test
    def "testOrderStateCombinedExpression2"() {
        expect:
        String combinedOrderStateExpress = '''
            {"conditions": [{"field": "activity", "op":"eq", "value":"LOTTERY"},{"field": "state", "op":"eq", "value":"PAID"}, 
                      {"field": "extra", "op":"notcontains", "value":"LOTTERY"}], "result":"待开奖"} 
                '''
        CombinedExpression combinedExpression = expressionJsonParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state": "PAID", "activity": "LOTTERY", "extra": [:]]) == "待开奖"
    }

    @Test
    def "testOrderStateCombinedExpression3"() {
        expect:
        String combinedOrderStateExpress = '''
            {"conditions": [{"field": "activity", "op":"eq", "value":"LOTTERY"},{"field": "state", "op":"eq", "value":"CONFIRM"}, 
                      {"field": "extra.EXT_STATUS", "op":"eq",  "value":"prize"}], "result":"待开奖"} 
                '''
        CombinedExpression combinedExpression = expressionJsonParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state": "CONFIRM", "activity": "LOTTERY", "extra": ['EXT_STATUS': 'prize']]) == "待开奖"
    }

    @Test
    def "testWholeExpressions"() {
        expect:
        String wholeExpressionStr = '''
            [{"cond": {"field": "state", "op":"eq", "value":"PAID"}, "result":"待发货"},
             {"conditions": [{"field": "activity", "op":"eq", "value":"LOTTERY"},{"field": "state", "op":"eq", "value":"CONFIRM"}], "result":"待开奖"}]
                '''

        WholeExpressions wholeExpressions = expressionJsonParser.parseWhole(wholeExpressionStr)
        wholeExpressions.getResult(["state": "PAID"]) == "待发货"
        wholeExpressions.getResult(["state": "CONFIRM", "activity": "LOTTERY"]) == "待开奖"

    }
}
