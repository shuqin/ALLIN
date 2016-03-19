package cc.lovesq.flinkdemo;

import cc.lovesq.model.DetectStatDO;
import cc.lovesq.repository.MongoRepository;
import cc.lovesq.util.MongodbUtil;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @Description 写入 mongodb
 * @Date 2021/5/7 6:36 下午
 * @Created by qinshu
 */
public class MongoRepositorySinkFunction extends RichSinkFunction<Tuple3<String, String, Integer>> {

    private String statType;

    private MongoRepository mongoRepository;

    public MongoRepositorySinkFunction(String statType) {
        this.statType = statType;
    }

    public void invoke(Tuple3<String, String, Integer> value) throws Exception {
        DetectStatDO detectStatDO = DetectStatDO.from(statType, value);
        mongoRepository.save(detectStatDO);
    }

    public void open(Configuration params) throws Exception {
        super.open(params);

        if (mongoRepository == null) {
            mongoRepository = MongodbUtil.getInstance();
        }

    }

    public void close() throws Exception {
    }

}
