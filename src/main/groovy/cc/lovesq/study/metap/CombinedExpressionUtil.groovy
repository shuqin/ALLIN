package cc.lovesq.study.metap

@Mixin(SingleExpUtil)
class CombinedExpressionUtil {

    CombinedExpression from(expstr) {
        def conds = expstr.split("&&")
        def expressions = conds.collect { cond -> from(cond.trim()) }
        new CombinedExpression(expressions: expressions)
    }
}
