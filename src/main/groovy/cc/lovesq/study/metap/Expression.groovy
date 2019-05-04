package cc.lovesq.study.metap

class Expression {

    def field
    def op
    def value

    private String inner() {
        "EXP[$field $op $value]"
    }

    def match(map) {
        map[field] == value
    }

    static void main(args) {
        def exp = new Expression(field: "id", op:"=", value:111)

        // 动态访问属性
        println exp.getProperty("value")
        exp.setProperty("value", 123)
        def valueProp = "value"
        println "exp[$valueProp] = ${exp[valueProp]}"
        println "exp.\"$valueProp\" = " + exp."$valueProp"

        // 轻松调用私有方法
        println exp.invokeMethod('inner', null)
        println exp.invokeMethod('match', [id: 123])
    }
}
