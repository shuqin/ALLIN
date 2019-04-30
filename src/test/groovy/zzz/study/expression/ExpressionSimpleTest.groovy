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
        'state = 5 => 待发货'  | 5 | '待发货'
        'state = 6 => 已发货'  | 6 | '已发货'
        'state = 99 => 已关闭'  | 99 | '已关闭'
    }

    @Test
    def "testOrderStateCombinedExpression"() {
        expect:
        String combinedOrderStateExpress = '''
           activity_type = 13 && state = 5 && tcExtra isnull => 待开奖
    '''
        CombinedExpression combinedExpression = expressionSimpleParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":5, "activity_type":13]) == "待开奖"

    }

    @Test
    def "testOrderStateCombinedExpression2"() {
        expect:
        String combinedOrderStateExpress = '''
               activity_type = 13 && state = 5 && tcExtra NCT LOTTERY => 待开奖
        '''
        CombinedExpression combinedExpression = expressionSimpleParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":5, "activity_type":13, "tcExtra":[:]]) == "待开奖"
    }

    @Test
    def "testOrderStateCombinedExpression3"() {
        expect:
        String combinedOrderStateExpress = '''
           activity_type = 13 && state = 50 && tcExtra.EXT_ORDER_STATUS = "40" => 待开奖
        '''
        CombinedExpression combinedExpression = expressionSimpleParser.parseCombined(combinedOrderStateExpress.trim())
        combinedExpression.getResult(["state":50, "activity_type":13, "tcExtra":['EXT_ORDER_STATUS':'40']]) == "待开奖"
    }

    @Test
    def "testWholeExpressions"() {
        expect:
        String wholeExpressionStr = '''
         activity_type = 13 && state = 5 && tcExtra NCT LOTTERY => 待开奖 ;
         state = 5 => 待发货 ; activity_type = 13 && state = 50 && tcExtra.EXT_ORDER_STATUS = "40" => 待开奖 ;
         
        '''

        WholeExpressions wholeExpressions = expressionSimpleParser.parseWhole(wholeExpressionStr)
        wholeExpressions.getResult(["state":5]) == "待发货"
        wholeExpressions.getResult(["state":5, "activity_type":13]) == "待开奖"
        wholeExpressions.getResult(["state":50, "activity_type":13, "tcExtra":['EXT_ORDER_STATUS':'40']]) == "待开奖"
        wholeExpressions.getResult(["state":99]) == ""

    }
}
