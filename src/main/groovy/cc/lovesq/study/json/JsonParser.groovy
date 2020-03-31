package cc.lovesq.study.json

import groovy.json.JsonSlurper

class JsonParser {

    def jsonSlurper = new JsonSlurper()

    def parse(json) {
        def obj = jsonSlurper.parseText(json)
        Map map = (Map) obj
        parseMap(map, 'Domain', this.&underscoreToCamelCase)
    }

    def parseMap(Map map, String namespace, keyConverter) {
        def classTpl = classTpl()
        def fields = ""
        map.each {
            k, v ->
                if (!(v instanceof Map) && !(v instanceof List)) {
                    fields += "${indent()}private ${getType(v)} $k;\n"
                }
                else {

                    if (v instanceof Map) {
                        def className = getClsName(k)
                        fields += "${indent()}private $className $k;\n"
                        parseMap(v, convert(className, keyConverter), keyConverter)
                    }

                    if (v instanceof List) {
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
                            fields += "${indent()}private List<${convert(cls,keyConverter)}> ${cls}s;\n"
                            parseMap(obj, convert(cls, keyConverter), keyConverter)
                        }
                    }
                }
        }
        print getString(classTpl, ["Namespace": namespace, "fieldsContent" : fields])

    }

    def getType(v) {
        if (v instanceof String) {
            return "String"
        }
        if (v instanceof Integer) {
            return "Integer"
        }
        if (v instanceof Boolean) {
            return "Boolean"
        }
        if (v instanceof Long) {
            return "Long"
        }
        if (v instanceof BigDecimal) {
            return "Double"
        }

        "String"
    }

    def getClsName(String str) {
        capitalize(str)
    }

    def capitalize(String str) {
        str[0].toUpperCase() + (str.length() >= 2 ? str[1..-1] : "")
    }

    def classTpl() {
        '''
class $Namespace implements Serializable {
$fieldsContent
}
        '''
    }

    def indent() {
        '    '
    }

    def getString(tplText, binding) {
        def engine = new groovy.text.SimpleTemplateEngine()
        return engine.createTemplate(tplText).make(binding)
    }

    def convert(key, convertFunc) {
        convertFunc == null ? key : convertFunc(key)
    }

    def underscoreToCamelCase(String underscore){
        String[] ss = underscore.split("_")
        if(ss.length ==1){
            return underscore
        }

        return ss[0] +  ss.collect { capitalize(it) }.join("")
    }
}
