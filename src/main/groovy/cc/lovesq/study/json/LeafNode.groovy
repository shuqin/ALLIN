package cc.lovesq.study.json

class LeafNode implements Node {

    String type
    String name
    Boolean isList = false

    @Override
    String desc() {
        def desc = ''
        if (isList) {
            desc += "private List<$type> $name;"
        }
        else {
            desc += "private $type $name;"
        }
        return desc
    }
}
