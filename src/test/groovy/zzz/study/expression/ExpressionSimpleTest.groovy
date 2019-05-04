package zzz.study.expression

import org.junit.Test
import spock.lang.Specification
import zzz.study.patterns.composite.expression.CombinedExpression
import zzz.study.patterns.composite.expression.SingleExpression
import zzz.study.patterns.composite.expression.WholeExpressions
import zzz.study.patterns.composite.expression.parser.ExpressionSimpleParser

class ExpressionSimpleTest extends Specification {

    ExpressionSimpleParser expressionSimpleParser = new ExpressionSimpleParser()

    @Test
    def "testOrderStateExpression"() {
        expect:
        SingleExpression singleExpression = expressionSimpleParser.parseSingle(singleOrderStateExpression)
        singleExpression.getResult(["state":value]) == result

        where:
        singleOrderStateExpression  | value | result
        'state = PAID => 待发货'  | "PAID" | '待发货'
    }

    @Test
    def "testOrderStateCombinedExpression"() {
        expect:
        String combinedOrderStateExpress = '''
           activity = LOTTERY && state = PAID && extra isnull => 待开奖
    '''
        CombinedExpression combinedExpression = expressionSimpleParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":"PAID", "activity":"LOTTERY"]) == "待开奖"

    }

    @Test
    def "testOrderStateCombinedExpression2"() {
        expect:
        String combinedOrderStateExpress = '''
               activity = LOTTERY && state = PAID && extra NCT LOTTERY => 待开奖
        '''
        CombinedExpression combinedExpression = expressionSimpleParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":"PAID", "activity":"LOTTERY", "extra":[:]]) == "待开奖"
    }

    @Test
    def "testOrderStateCombinedExpression3"() {
        expect:
        String combinedOrderStateExpress = '''
           activity = LOTTERY && state = CONFIRM && extra.EXT_STATUS = "prize" => 待开奖
        '''
        CombinedExpression combinedExpression = expressionSimpleParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":"CONFIRM", "activity":"LOTTERY", "extra":['EXT_STATUS':'prize']]) == "待开奖"
    }

    @Test
    def "testWholeExpressions"() {
        expect:
        String wholeExpressionStr = '''
         activity = LOTTERY && state = PAID && extra NCT LOTTERY => 待开奖 ;
         state = PAID => 待发货 ; activity = LOTTERY && state = CONFIRM && extra.EXT_STATUS = "prize" => 待开奖 ;
         
        '''

        WholeExpressions wholeExpressions = expressionSimpleParser.parseWhole(wholeExpressionStr)
        wholeExpressions.getResult(["state":"PAID"]) == "待发货"
        wholeExpressions.getResult(["state":"PAID", "activity":"LOTTERY"]) == "待开奖"
        wholeExpressions.getResult(["state":"CONFIRM", "activity":"LOTTERY", "extra":['EXT_STATUS':'prize']]) == "待开奖"

    }
}
