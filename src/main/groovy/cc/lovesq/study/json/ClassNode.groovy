package cc.lovesq.study.json

import static cc.lovesq.study.json.Common.*

class ClassNode implements Node {

    String className
    List<LeafNode> leafNodes
    List<ClassNode> classNodes

    @Override
    String desc() {
        def clsTpl = Common.classTpl()
        def fields = ""
        fields += leafNodes.collect { it.desc() }.join("")
        fields += classNodes.each { convert(it.className, Common.&underscoreToCamelCase) + " " + uncapitalize(it.className) }.join("")
        def classDef = getString(clsTpl, ["Namespace": className, "fieldsContent" : fields])

        def resultstr = classDef

        classNodes.each {
            resultstr += it.desc()
        }
        return resultstr
    }


}
