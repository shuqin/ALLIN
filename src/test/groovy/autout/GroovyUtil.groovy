package autout

import com.alibaba.fastjson.JSON

/**
 * Created by shuqin on 18/6/25.
 */
class GroovyUtil {

    /**
     * Map 转 对象
     */
    static <T> T toObject(map, Class<T> c) {
        def json = JSON.toJSONString(map)
        def obj = JSON.parseObject(json, c)
        return obj
    }
}
