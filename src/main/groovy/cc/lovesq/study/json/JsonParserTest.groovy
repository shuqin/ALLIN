package cc.lovesq.study.json

class JsonParserTest {

    def static main(args) {

        /*
        def json = '''
               {"error":0,"status":"success","date":"2014-05-10","humidity":[], "comment":null, "extra":{"rain":3,"sunny":2},"recorder":{"name":"qin","time":"2014-05-10 22:00","mood":"good","address":{"provice":"ZJ","city":"nanjing"}},"results":[{"currentCity":"南京","weather_data":[{"date":"周六今天,实时19","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/dayu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/dayu.png","weather":"大雨","temperature":"18"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","wind":"西北风4-5级","temperature":"21~14"}]}]}
               '''
        */

        def json = '''
{"showPrice":100,"shopModel":{"logo":"https://img.yzcdn.cn/upload_files/2020/02/13/FkWDWq_kDq0UBKd-sfWs4fJO6QX9.jpg","shopName":"啊专营店"},"shopConfigModel":{"isYouzanSecured":1},"itemBasicModel":{"sellPoint":"ut","pictureHeight":-65165311,"video":{"videoUrl":"dolore","coverHeight":"laborum sed","coverUrl":"amet in","categoryId":-63816665,"videoName":"ex quis Lorem eiusmod Ut","videoDuration":-86883503,"coverWidth":"enim Lorem est irure","playedCount":-44451855,"videoSize":-20086384,"videoPath":"dolore ali","countPlayedUrl":"incididunt ea in eu culpa","bucketId":16234304,"videoId":-49494159,"status":-57184148,"isPublished":-27920928},"pictures":[{"width":70657919,"height":-22343252,"id":-10598521,"url":"consequat eu Lorem in"}],"kdtId":-5139550,"title":"et ex"},"goodsDetail":[{"color":"#f9f9f9","fullscreen":0,"type":"rich_text","content":"<p>我是富文本</p>"}],"updateDate":1234234234,"serviceDescList":[{"tag":"线下门店","desc":"该店铺已缴纳消费保障保证金，订单维权更快更高效，请放心购买"},{"tag":"商品含税","desc":"您购买的商品已包含跨境电商进口税，您无需再进行支付"}],"skus":[{"k":"尺寸","k_id":2,"v":"11","v_id":1442}]}
                   '''

        //new JsonParser().parse(json)
        new JsonParserV2().parse(json)


        def json2 = '''

{"agentEventDatas": [{"rule_id":"反弹shell","format_output":"进程 pname 反向连接到 %dest_ip%:%dest_port%","info":{"process_events":{"pid":21,"pname":"nginx","cmdline":"curl www.cfda.com","ppid":7,"ppname":"bash"},"proc_trees":[{"pid":21,"pname":"nginx","cmdline":"curl www.cfda.com","ppid":7,"ppname":"bash"}],"containers":{"container_id":"fef4636d8403871c2e56e06e51d609554564adbbf8284dd914a0f61130558bdf","container_name":"nginx","image_id":"4eb8f7c43909449dbad801c50d9dccc7dc86631e54f28b1a4b13575729065be8","status":"Running"},"sockets":{"src_ip":"127.0.0.1","src_port":"8080","type":"1","in_out":"0","dest_ip":"localhost","dest_port":"80"}}}]}

        '''
        new JsonParserV2().parse(json2);
    }

}


