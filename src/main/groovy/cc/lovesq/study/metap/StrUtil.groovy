package cc.lovesq.study.metap

class StrUtil {

    Expression convertFrom(expstr) {
        def (field, op, value) = expstr.split(" ")
        new Expression(field: field, op: op, value: value)
    }
}
