package shared.util;

import cc.lovesq.model.event.AgentDetectEventData;
import org.junit.Assert;
import org.junit.Test;
import shared.utils.JsonUtil;

import java.io.*;
import java.util.List;

public class JsonUtilTest {

    @Test
    public void testParseJson() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/json.txt");
            byte[] bytes = new byte[4096];
            int num = 0;
            String json = "";
            while((num=is.read(bytes))>0){
                json=new String(bytes,0,num);
            }
            List<AgentDetectEventData> ade = JsonUtil.jsonToObject(json, List.class, AgentDetectEventData.class);
            Assert.assertNotNull(ade);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
