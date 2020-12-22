package cc.lovesq.goodssnapshot.impl4;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Component
public class ServiceTplList4 {

    @Value(value="classpath:service4.tpl")
    private Resource data;

    private List<ServiceTpl4> serviceTplList4 = new ArrayList<>();

    private static Map<String, List<ServiceTpl4>> serviceTplMap = new HashMap<>();

    @PostConstruct
    public void init() {
        String json = getData();
        serviceTplList4 = JSONObject.parseArray(json, ServiceTpl4.class);

        for (ServiceTpl4 serviceTpl4: serviceTplList4) {
            if (!serviceTplMap.containsKey(serviceTpl4.getKey())) {
                serviceTplMap.put(serviceTpl4.getKey(), new ArrayList<>());
            }
            serviceTplMap.get(serviceTpl4.getKey()).add(serviceTpl4);
        }
    }

    public boolean containsKey(String key) {
        return serviceTplMap.containsKey(key);
    }

    public ServiceTpl4 getTpl(String key, long timestamp) {
        List<ServiceTpl4> serviceTpls = serviceTplMap.get(key);
        if (CollectionUtils.isEmpty(serviceTpls)) {
            return null;
        }

        for (ServiceTpl4 serviceTpl: serviceTpls) {
            if (serviceTpl.getStart() <= timestamp && serviceTpl.getEnd() > timestamp) {
                return serviceTpl;
            }
        }
        return null;
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
