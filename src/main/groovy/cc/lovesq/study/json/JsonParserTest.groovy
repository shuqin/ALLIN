package cc.lovesq.study.json

class JsonParserTest {

    def static main(args) {

        def json = '''
               {"error":0,"status":"success","date":"2014-05-10","humidity":[], "comment":null, "extra":{"rain":3,"sunny":2},"recorder":{"name":"qin","time":"2014-05-10 22:00","mood":"good","address":{"provice":"ZJ","city":"nanjing"}},"results":[{"currentCity":"南京","weather_data":[{"date":"周六今天,实时19","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/dayu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/dayu.png","weather":"大雨","temperature":"18"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","wind":"西北风4-5级","temperature":"21~14"}]}]}
               '''

        //new JsonParser().parse(json)
        new JsonParserV2().parse(json)
    }

}


