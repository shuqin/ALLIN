package cc.lovesq.model;

import lombok.experimental.Accessors;
import org.apache.flink.api.java.tuple.Tuple3;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description 检测统计
 * @Date 2021/5/7 6:25 下午
 * @Created by qinshu
 */
@Accessors(chain=true)  //链式调用
@Document(collection="detect_stat")   //指定集合
@CompoundIndexes({
        @CompoundIndex(
                name = "ns_ts",
                def = "{\"timestamp\": 1, \"statType\": 1}",
                background = true
        )
})
public class DetectStatDO {

    @Id
    private String id;
    private String statType;
    private String type;
    private String desc;
    private Integer count;

    @Indexed
    private Long timestamp;

    public static DetectStatDO from(String statType, Tuple3<String, String, Integer> tuple3) {
        DetectStatDO detectStatDO = new DetectStatDO();
        detectStatDO.statType = statType;
        detectStatDO.type = tuple3.f0;
        detectStatDO.desc = tuple3.f1;
        detectStatDO.count = tuple3.f2;
        detectStatDO.timestamp = System.currentTimeMillis();  // should be stat timestamp
        return detectStatDO;
    }

    public String getId() {
        return id;
    }

    public String getStatType() {
        return statType;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCount() {
        return count;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
