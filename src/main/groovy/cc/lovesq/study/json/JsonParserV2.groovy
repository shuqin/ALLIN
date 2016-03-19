package cc.lovesq.study.json

import groovy.json.JsonOutput

class JsonParserV2 {

    def parse(json) {
        def classNode = new ClassNodeBuilder().build(json)
        println(JsonOutput.toJson(classNode))
        print classNode.desc()
    }
}
