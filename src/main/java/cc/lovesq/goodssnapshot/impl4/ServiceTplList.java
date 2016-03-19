package cc.lovesq.goodssnapshot.impl4;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.sun.jmx.mbeanserver.Util.cast;

@Component
public class ServiceTplList implements ServiceTplListInf {

    private static Map<String, List<ServiceTpl>> serviceTplMap = new HashMap<>();
    private static Set<String> uniqueKeys = new HashSet<>();
    @Value(value = "classpath:service.tpl")
    private Resource data;
    private List<ServiceTpl> serviceTplList = new ArrayList<>();
    private WatchService watchService;

    @PostConstruct
    public void init() throws IOException {

        convertToList();

        watchService = FileSystems.getDefault().newWatchService();
        System.out.println("parent: " + data.getFile().getParent());
        Paths.get(data.getFile().getParent()).register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        new Thread(() -> listenFileModified()).start();
    }

    private void convertToList() {

        String json = getData();
        serviceTplList = JSONObject.parseArray(json, ServiceTpl.class);

        Map<String, List<ServiceTpl>> serviceTplLocalMap = new HashMap<>();
        for (ServiceTpl serviceTpl : serviceTplList) {
            String key = serviceTpl.getKey();
            String uniqueKey = serviceTpl.getUniqueKey();
            if (!serviceTplLocalMap.containsKey(key)) {
                serviceTplLocalMap.put(key, new ArrayList<>());
                uniqueKeys.add(uniqueKey);
            }
            serviceTplLocalMap.get(key).add(serviceTpl);
        }
        serviceTplMap = serviceTplLocalMap;
    }

    private void listenFileModified() {
        try {
            while (true) {
                WatchKey key = watchService.poll(20, TimeUnit.SECONDS);
                if (key == null) {
                    continue;
                }
                //获取监听事件
                for (WatchEvent<?> event : key.pollEvents()) {
                    //获取监听事件类型
                    WatchEvent.Kind kind = event.kind();
                    //异常事件跳过
                    if (kind != StandardWatchEventKinds.ENTRY_MODIFY) {
                        continue;
                    }
                    //获取监听Path
                    Path path = cast(event.context());
                    //只关注目标文件
                    String fileName = data.getFile().getName();
                    if (!fileName.equals(path.toString())) {
                        continue;
                    }
                    convertToList();

                }
                //处理监听key后(即处理监听事件后)，监听key需要复位，便于下次监听
                key.reset();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }

    }

    public boolean containsKey(String key) {
        return serviceTplMap.containsKey(key);
    }

    public ServiceTpl getTpl(String key, long timestamp) {
        List<ServiceTpl> serviceTpls = serviceTplMap.get(key);
        if (CollectionUtils.isEmpty(serviceTpls)) {
            return null;
        }

        for (ServiceTpl serviceTpl : serviceTpls) {
            if (serviceTpl.getStart() <= timestamp && serviceTpl.getEnd() > timestamp) {
                return serviceTpl;
            }
        }
        return null;
    }

    public String getData() {
        try {
            File file = data.getFile();
            String jsonData = this.jsonRead(file);
            return jsonData;
        } catch (Exception e) {
            return null;
        }
    }

    private String jsonRead(File file) {
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
