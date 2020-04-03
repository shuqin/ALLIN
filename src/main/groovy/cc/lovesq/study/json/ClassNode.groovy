package cc.lovesq.study.json

import org.apache.commons.collections.CollectionUtils

import static cc.lovesq.study.json.Common.*

class ClassNode implements Node {

    String className = ""
    List<LeafNode> leafNodes = []
    List<ClassNode> classNodes = []
    Boolean isInList = false

    @Override
    String desc() {
        def clsTpl = Common.classTpl()

        def fields = ""
        fields += leafNodes.collect { indent() + it.desc() }.join("\n")
        def classDef = getString(clsTpl, ["Namespace": className, "fieldsContent" : fields])
        if (CollectionUtils.isEmpty(classNodes)) {
            return classDef
        }

        fields += "\n" + classNodes.findAll { it.isInList == false }.collect { "${indent()}private ${it.className} ${uncapitalize(it.className)};" }.join("\n")
        def resultstr = getString(clsTpl, ["Namespace": className, "fieldsContent" : fields])
        resultstr += classNodes.collect { it.desc() }.join("\n")
        return resultstr
    }

    boolean addNode(LeafNode node) {
        leafNodes.add(node)
        true
    }

    boolean addNode(ClassNode classNode) {
        classNodes.add(classNode)
        true
    }
}
