package cc.lovesq.study.json

class LeafNode implements Node {

    String type
    String name
    Boolean isList = false

    LeafNode(type, name) {
        this.type = type
        this.name = name
    }

    LeafNode(type, name, isList) {
        this.type = type
        this.name = name
        this.isList = isList
    }

    @Override
    String desc() {
        isList ? Common.getString("private List<$type> $name;", ["type": type, "name": name]) :
                Common.getString("private $type $name;", ["type": type, "name": name])
    }

}
