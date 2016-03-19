package cc.lovesq.study.basics

class MapUtil {

    def static groupByValue(Map originMap) {
        def groupedMap = [:]
        originMap.each {
            key, value ->
                def valueList = groupedMap.getOrDefault(value, [])
                valueList.add(key)
                groupedMap.put(value, valueList)
        }
        groupedMap
    }

    def static main(args) {
        def originMap = ["testA": "success", "testB": "failed", "testC": "success", "testD": "hhaa"]
        println groupByValue(originMap)
    }
}
