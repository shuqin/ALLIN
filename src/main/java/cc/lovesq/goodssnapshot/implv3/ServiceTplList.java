package cc.lovesq.goodssnapshot.implv3;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class ServiceTplList {

    @Value(value="classpath:service.tpl")
    private Resource data;

    private List<ServiceTpl> serviceTplList = new ArrayList<>();

    @PostConstruct
    public void init() {
        String json = getData();
        serviceTplList = JSONObject.parseArray(json, ServiceTpl.class);
    }

    public List<ServiceTpl> getUnmodified() {
        return Collections.unmodifiableList(serviceTplList);
    }

    public String getData(){
        try {
            File file = data.getFile();
            String jsonData = this.jsonRead(file);
            return jsonData;
        } catch (Exception e) {
            return null;
        }
    }

    private String jsonRead(File file){
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }
}
