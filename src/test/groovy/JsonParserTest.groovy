import cc.lovesq.study.json.JsonParser
import org.junit.Test
import spock.lang.Specification

class JsonParserTest extends Specification {

    @Test
    def "testParseJson"() {
        when:
        def json = '''
{"error":0,"status":"success","date":"2014-05-10","extra":{"rain":3,"sunny":2},"recorder":{"name":"qin","time":"2014-05-10 22:00","mood":"good","address":{"provice":"ZJ","city":"nanjing"}},"results":[{"currentCity":"南京","weather_data":[{"date":"周六今天,实时19","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/dayu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/dayu.png","weather":"大雨","wind":"东南风5-6级","temperature":"18"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"阵雨转多云","wind":"西北风4-5级","temperature":"21~14"}]}]}
                   '''

        new JsonParser().parse(json)
        then:
        1==1
    }


}


