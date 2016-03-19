package cc.lovesq.study.metap

class CombinedExpression {

    List<Expression> expressions

    def desc() {
        "[" + expressions?.collect { it.invokeMethod('inner', null) }?.join(",") + "]"
    }

    static void main(args) {

        // 动态混入
        CombinedExpression.mixin CombinedExpressionUtil
        def ce = new CombinedExpression().from("state = 6 && type = 1")
        println ce.desc()

        println new CombinedExpression().desc()

    }
}
