package cc.lovesq.flinkdemo;

import cc.lovesq.model.DetectEventDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * @Description 消息水位线设置
 * @Date 2021/5/7 2:58 下午
 * @Created by qinshu
 */
public class MessageWaterEmitter implements AssignerWithPunctuatedWatermarks<String> {
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(String lastElement, long extractedTimestamp) {
        if (StringUtils.isNotBlank(lastElement)) {
            DetectEventDTO detectEventDTO = JSON.parseObject(lastElement, DetectEventDTO.class);
            return new Watermark(detectEventDTO.getTimestamp());
        }
        return null;
    }

    @Override
    public long extractTimestamp(String element, long previousElementTimestamp) {
        if (StringUtils.isNotBlank(element)) {
            DetectEventDTO detectEventDTO = JSON.parseObject(element, DetectEventDTO.class);
            return detectEventDTO.getTimestamp();
        }
        return 0l;
    }
}
