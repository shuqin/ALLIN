package cc.lovesq.study.metap

@Mixin(StrUtil)
class ExpressionWithMaxin extends Expression {

    def cons(str) {
        // 静态 mixin
        convertFrom(str)
    }

    static void main(args) {
        def exp = new ExpressionWithMaxin().cons('state = 5')
        println exp.invokeMethod('inner', null)
        println exp.match(['state': '5'])

        // 动态 mixin
        ExpressionWithMaxin.mixin StrUtil
        def exp2 = new ExpressionWithMaxin().convertFrom('state = 6')
        println exp2.invokeMethod('inner', null)
        println exp2.match(['state': '6'])
    }
}
