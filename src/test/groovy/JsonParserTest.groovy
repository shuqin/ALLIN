import cc.lovesq.study.json.JsonParser
import org.junit.Test
import spock.lang.Specification

class JsonParserTest extends Specification {

    @Test
    def "testParseJson"() {
        when:
        def json = '''{"note":"haha","match":false, "extra": {"price": 1.0, "num":5}, "list":["i", "have", "a", "dream"],
                        "skus":[{"id":-30477776,"name":"Lorem minim sed ex","price":54627923}]}'''

        new JsonParser().parse(json)
        then:
        1==1
    }


}


