package cc.lovesq.study.metap

class DynamicCreating {

    static void main(args) {
        def exp = new Expando(field: "id", op: "=", value: 111,
                inner: {
                    "EXP[$field $op $value]"
                })

        exp.match = { map ->
            map[field] == value
        }

        println exp.getProperty("value")
        exp.setProperty("value", 123)
        def valueProp = "value"
        println "exp[$valueProp] = ${exp[valueProp]}"
        println "exp.\"$valueProp\" = " + exp."$valueProp"

        println exp.invokeMethod('inner', null)
        println(exp.match([id: 123]))
    }
}
