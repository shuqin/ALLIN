package cc.lovesq.study.json

import groovy.json.JsonSlurper
import org.apache.commons.collections.CollectionUtils

import static cc.lovesq.study.json.Common.*

class ClassNodeBuilder {

    def jsonSlurper = new JsonSlurper()

    def build(json) {
        def obj = jsonSlurper.parseText(json)
        Map map = (Map) obj
        return parseMap(map, 'Domain')
    }

    def static parseMap(Map map, String namespace) {
        ClassNode classNode = new ClassNode(className: namespace)
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
            classNode.addNode(new LeafNode(type: getType(v), name: k))
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

        /* 是否要遍历列表中的所有元素来拼接成一个完整的对象，适用于 json 的返回数据字段可能有不全的情形 */
        private static isTravelFull = true

        @Override
        def add(ClassNode classNode, Object k, Object v) {
            v = (List)v
            if (CollectionUtils.isEmpty(v)) {
                return
            }
            def obj = v.get(0)
            if (!(obj instanceof Map) && !(obj instanceof List)) {
                def type = getType(obj)
                classNode.addNode(new LeafNode(type: "$type", name: "${type}s", isList: true))
            }

            if (obj instanceof Map) {

                def cls = getClsName(underscoreToCamelCase(k))
                if (cls.endsWith('s')) {
                    cls = cls[0..-2]
                }
                classNode.addNode(new LeafNode(type: "${cls}", name: "${uncapitalize(cls)}s", isList:  true))

                addSubClassNode(classNode, obj, v, cls)

            }

        }

        private void addSubClassNode(ClassNode classNode, obj, v, cls) {
            def subObj = obj

            if (isTravelFull) {
                subObj = mergeToFull(v)
            }

            def subClassNode = parseMap(subObj, cls)
            subClassNode.isInList = true
            classNode.addNode(subClassNode)
        }

        private Map mergeToFull(List<Map> v) {
            Map full = [:]
            v.forEach {
                map ->
                    map.forEach {
                        k, subv ->
                            if (full.get(k) == null) {
                                full.put(k, subv)
                            }
                            def exist = full.get(k)
                            full.put(k, merge(exist, subv))
                    }
            }
            return full
        }

        def merge(exist, subv) {

            // 基本类型 : 取哪个值都一样
            if (!exist instanceof List && !exist instanceof Map) {
                return exist
            }

            // List : 已有的和当前比较，如果已有的为空列表，当前非空，则用当前替代已有的
            if (exist instanceof List && CollectionUtils.isEmpty(exist) && CollectionUtils.isNotEmpty(subv)) {
                return subv
            }

            // Map : 两个 map size 不一样, 则 key 一定有不一样的 ， 合并两个 map
            if (exist instanceof Map && subv != null && exist.size() != (Map)subv.size()) {
                return mergeToFull([exist, subv])
            }
            else {
                return exist
            }
        }

    }

}
