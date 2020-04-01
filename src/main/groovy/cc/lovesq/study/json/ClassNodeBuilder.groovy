package cc.lovesq.study.json

import groovy.json.JsonSlurper

import static cc.lovesq.study.json.Common.*

class ClassNodeBuilder {

    def jsonSlurper = new JsonSlurper()

    def build(json) {
        def obj = jsonSlurper.parseText(json)
        Map map = (Map) obj
        return parseMap(map, 'Domain')
    }

    def parseMap(Map map, String namespace) {
        ClassNode classNode = new ClassNode()
        classNode.className = namespace
        map.each {
            k, v ->
                if (!(v instanceof Map) && !(v instanceof List)) {
                    classNode.addNode(new LeafNode(getType(v), k))
                }
                else {

                    if (v instanceof Map) {
                        def className = getClsName(k)
                        //classNode.addNode(new LeafNode(className, k))
                        classNode.addNode(parseMap(v, className))
                    }

                    if (v instanceof List) {
                        def obj = v.get(0)
                        if (!(obj instanceof Map) && !(obj instanceof List)) {
                            def type = getType(obj)
                            classNode.addNode(new LeafNode("$type", "${type}s", true))
                        }
                        if (obj instanceof Map) {
                            def cls = getClsName(k)
                            if (cls.endsWith('s')) {
                                cls = cls[0..-2]
                            }
                            classNode.addNode(new LeafNode("${cls}", "${cls}s", true))
                            classNode.addNode(parseMap(obj, cls))
                        }
                    }
                }

        }
        return classNode
    }

}
