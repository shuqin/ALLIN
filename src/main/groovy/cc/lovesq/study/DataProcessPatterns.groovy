package cc.lovesq.study

/**
 * Created by shuqin on 18/2/3.
 */
class DataProcessPatterns {

    /**
     * 如果条件为真，则取valueActionIfTrue计算得到的结果，否则取指定默认值defaultValue
     */
    static <P, R> R getValue(Closure<Boolean> cond, Closure<R> valueActionIfTrue, P p, R defaultValue) {
        cond.call(p) ? valueActionIfTrue.call(p) : defaultValue
    }

    /**
     * 选择满足条件的第一个元素，否则取指定默认值defaultValue
     */
    static <R> R selectFirstMatched(Closure<Boolean> cond, List<R> elems, R defaultValue) {
        def matchedValue = elems.find(cond)
         matchedValue ? matchedValue : defaultValue
    }

}
