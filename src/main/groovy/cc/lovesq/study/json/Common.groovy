package cc.lovesq.study.json

class Common {

    def static getType(v) {
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

    def static getClsName(String str) {
        capitalize(str)
    }

    def static capitalize(String str) {
        str[0].toUpperCase() + (str.length() >= 2 ? str[1..-1] : "")
    }

    def static uncapitalize(String str) {
        str[0].toLowerCase() + (str.length() >= 2 ? str[1..-1] : "")
    }

    def static classTpl() {
        '''
@Data
public class $Namespace implements Serializable {
$fieldsContent
}

        '''
    }

    def static indent() {
        '    '
    }

    def static getString(tplText, binding) {
        def engine = new groovy.text.SimpleTemplateEngine()
        return engine.createTemplate(tplText).make(binding).toString()
    }

    def static convert(key, convertFunc) {
        convertFunc == null ? key : convertFunc(key)
    }

    def static underscoreToCamelCase(String underscore){
        String[] ss = underscore.split("_")
        if(ss.length ==1){
            return underscore
        }

        return ss.collect { capitalize(it) }.join("")
    }
}
