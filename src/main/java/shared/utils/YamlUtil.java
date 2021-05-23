package shared.utils;

import org.yaml.snakeyaml.Yaml;

/**
 * @Description yaml 配置工具类
 * @Date 2021/5/23 11:53 上午
 * @Created by qinshu
 */
public class YamlUtil {

    /**
     * 通过 yml 文件加载对象模型
     */
    public static <T> T load(String ymlFilePath, Class<T> cls) {
        Yaml yaml = new Yaml();
        T model = yaml.loadAs(cls.getClassLoader().getResourceAsStream(ymlFilePath), cls);
        return model;
    }
}
