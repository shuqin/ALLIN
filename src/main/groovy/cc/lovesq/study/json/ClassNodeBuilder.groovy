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

    def static parseMap(Map map, String namespace) {
        ClassNode classNode = new ClassNode()
        classNode.className = namespace
        map.each {
            k, v ->
                getStratgey(v).add(classNode, k, v)
        }
        classNode
    }

    def static plainStrategy = new AddLeafNodeStrategy()
    def static mapStrategy = new AddMapNodeStrategy()
    def static listStrategy = new AddListNodeStrategy()

    def static getStratgey(Object v) {
        if (v instanceof Map) {
            return mapStrategy
        }

        if (v instanceof List) {
            return listStrategy
        }
        return plainStrategy
    }

    interface AddNodeStrategy {
        def add(ClassNode classNode, k, v)
    }

    static class AddLeafNodeStrategy implements AddNodeStrategy {

        @Override
        def add(ClassNode classNode, Object k, Object v) {
            classNode.addNode(new LeafNode(getType(v), k))
        }
    }

    static class AddMapNodeStrategy implements AddNodeStrategy {

        @Override
        def add(ClassNode classNode, Object k, Object v) {
            v = (Map)v
            def className = getClsName(k)
            classNode.addNode(parseMap(v, className))
        }
    }

    static class AddListNodeStrategy implements AddNodeStrategy {

        @Override
        def add(ClassNode classNode, Object k, Object v) {
            v = (List)v
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

                def subClassNode = parseMap(obj, cls)
                subClassNode.isInList = true
                classNode.addNode(subClassNode)
            }
        }
    }

}
