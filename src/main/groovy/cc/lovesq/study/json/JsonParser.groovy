package cc.lovesq.study.json

import groovy.json.JsonSlurper
import org.apache.commons.collections.CollectionUtils

import static cc.lovesq.study.json.Common.*

class JsonParser {

    def jsonSlurper = new JsonSlurper()

    def parse(json) {
        def obj = jsonSlurper.parseText(json)
        Map map = (Map) obj
        parseMap(map, 'Domain', Common.&underscoreToCamelCase)
    }

    def parseMap(Map map, String namespace, keyConverter) {
        def classTpl = classTpl()
        def fields = ""
        map.each {
            k, v ->
                if (!(v instanceof Map) && !(v instanceof List)) {
                    fields += "${indent()}private ${getType(v)} $k;\n"
                } else {

                    if (v instanceof Map) {
                        def className = getClsName(k)
                        fields += "${indent()}private $className $k;\n"
                        parseMap(v, convert(className, keyConverter), keyConverter)
                    }

                    if (v instanceof List && CollectionUtils.isNotEmpty(v)) {
                        def obj = v.get(0)
                        if (!(obj instanceof Map) && !(obj instanceof List)) {
                            def type = getType(obj)
                            fields += "${indent()}private List<$type> ${type}s;\n"
                        }
                        if (obj instanceof Map) {
                            def cls = getClsName(k)
                            if (cls.endsWith('s')) {
                                cls = cls[0..-2]
                            }
                            def convertedClsName = convert(cls, keyConverter)
                            fields += "${indent()}private List<${convertedClsName}> ${uncapitalize(convertedClsName)}s;\n"
                            parseMap(obj, convertedClsName, keyConverter)
                        }
                    }
                }
        }
        print getString(classTpl, ["Namespace": namespace, "fieldsContent": fields])

    }


}
