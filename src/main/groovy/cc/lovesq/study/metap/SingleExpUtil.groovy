package cc.lovesq.study.metap

class SingleExpUtil {

    Expression from(expstr) {
        def (field, op, value) = expstr.split(" ")
        new Expression(field: field, op: op, value: value)
    }

}

