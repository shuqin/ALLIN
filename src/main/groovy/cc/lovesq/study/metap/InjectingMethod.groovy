package cc.lovesq.study.metap

class InjectingMethod {

    static void main(args) {

        [id: 123, name: 'qin', 'skills': 'good'].each {
            println it
        }

        use(MapUtil) {
            def map = [id: 123, name: 'qin', 'skills': 'good']
            println map.pretty()
        }
    }

}

@Category(Map)
class MapUtil {
    def pretty() {
        "[" + this.collect { it }.join(",") + "]"
    }
}