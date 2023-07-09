package shared.util;

import cc.lovesq.model.BookInfo;
import cc.lovesq.model.event.AgentDetectEventData;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import shared.utils.JsonUtils;
import zzz.study.foundations.iolearn.RWTool;

import java.util.List;

public class JsonUtilTest {

    @Test
    public void testParseJson() {
        String json = RWTool.readFromSource("/json.txt");
        List<AgentDetectEventData> ade = JsonUtils.jsonToObject(json, new TypeReference<List<AgentDetectEventData>>() {
        });
        Assert.assertNotNull(ade);
    }

    @Test
    public void testParseJson2() {
        String json = RWTool.readFromSource("/json.txt");
        List<AgentDetectEventData> ade = JsonUtils.jsonToObject(json, List.class, AgentDetectEventData.class);
        Assert.assertNotNull(ade);
    }

    @Test
    public void testParseSimpleNestedJson() {
        String json = "{\"goods\":{\"desc\":\"2箱*250g\",\"goodsId\":8866,\"orderNo\":\"E20210522120237009258\",\"shopId\":659494,\"title\":\"认养一头牛\"},\"order\":{\"bookTime\":1621656157,\"codPay\":false,\"deliveryType\":\"express\",\"orderNo\":\"E20210522120237009258\",\"shopId\":659494,\"userId\":1476}}";

        BookInfo bookInfo = JsonUtils.toObject(json, BookInfo.class);
        Assert.assertNotNull(bookInfo);
    }
}
