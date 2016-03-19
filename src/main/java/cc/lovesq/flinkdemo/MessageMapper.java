package cc.lovesq.flinkdemo;

import cc.lovesq.model.DetectEventDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;


/**
 * @Description 消息映射
 * @Date 2021/5/7 2:57 下午
 * @Created by qinshu
 */
public class MessageMapper implements MapFunction<String, DetectEventDTO> {

    @Override
    public DetectEventDTO map(String value) throws Exception {
        if (StringUtils.isNotBlank(value)) {
            DetectEventDTO detectEventDTO = JSON.parseObject(value, DetectEventDTO.class);
            return detectEventDTO;
        }
        return null;
    }
}
