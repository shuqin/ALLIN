package cc.lovesq.study.metap

@Mixin(SingleExpUtil)
class ExpressionWithMixin extends Expression {

    def cons(str) {
        // 静态 mixin
        from(str)

    }

    static void main(args) {
        def exp = new ExpressionWithMixin().cons('state = 5')
        println exp.invokeMethod('inner', null)
        println exp.match(['state': '5'])

    }
}
