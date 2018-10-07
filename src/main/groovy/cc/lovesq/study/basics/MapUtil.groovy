package cc.lovesq.study.basics

class MapUtil {

    def static groupByValue(Map originMap) {
        def groupedMap = [:]
        originMap.each {
            key, value ->
                groupedMap.getOrDefault(value, []).add(key)
                groupedMap.put(value, groupedMap.get(value))
        }
        groupedMap
    }

    def static main(args) {
        def originMap = ["testA": "success", "testB": "failed", "testC": "success", "testD": "hhaa"]
        println groupByValue(originMap)
    }
}
