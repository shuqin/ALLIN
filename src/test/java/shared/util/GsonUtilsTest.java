package shared.util;

import cc.lovesq.model.BookInfo;
import cc.lovesq.model.event.AgentDetectEventData;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import shared.utils.GsonUtils;
import zzz.study.foundations.iolearn.RWTool;

import java.util.List;

public class GsonUtilsTest {

    @Test
    public void testParseJson() {
        String json = RWTool.readFromSource("/json.txt");
        List<AgentDetectEventData> ade = GsonUtils.fromJson(json, new TypeToken<List<AgentDetectEventData>>() {
        }.getType());
        Assert.assertNotNull(ade);
    }

    @Test
    public void testParseSimpleNestedJson() {
        String json = "{\"goods\":{\"desc\":\"2箱*250g\",\"goodsId\":8866,\"orderNo\":\"E20210522120237009258\",\"shopId\":659494,\"title\":\"认养一头牛\"},\"order\":{\"bookTime\":1621656157,\"codPay\":false,\"deliveryType\":\"express\",\"orderNo\":\"E20210522120237009258\",\"shopId\":659494,\"userId\":1476}}";

        BookInfo bookInfo = GsonUtils.fromJson(json, BookInfo.class);
        Assert.assertNotNull(bookInfo);
    }
}
