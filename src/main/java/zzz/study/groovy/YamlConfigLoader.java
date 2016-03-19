package zzz.study.groovy;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuankui on 17/6/13.
 */
public class YamlConfigLoader {

    public static ReportFieldConfig loadConfig(String content) {
        try {
            YamlReader reader = new YamlReader(content);
            Object object = reader.read();
            return JSON.parseObject(JSON.toJSONString(object), ReportFieldConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("load config failed:" + content, e);
        }
    }

    public static List<ReportFieldConfig> loadConfigs(List<String> contents) {
        return contents.stream().map(YamlConfigLoader::loadConfig).collect(Collectors.toList());
    }
}
