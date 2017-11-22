package zzz.study.groovy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shuqin on 17/11/22.
 */
public class FieldsConfigLoader {

  private static Logger logger = LoggerFactory.getLogger(FieldsConfigLoader.class);

  private static Map<String, ReportFieldConfig> fieldConfigMap = new HashMap<>();
  static {
    try {
      List<ReportFieldConfig> fieldConfigs = new YamlConfigDirLoader("src/main/resources/scripts/").loadConfigs();
      fieldConfigs.forEach(
          fc -> fieldConfigMap.put(fc.getName(), fc)
      );
      logger.info("fieldConfigs: {}", fieldConfigs);
    } catch (Exception ex) {
      logger.error("failed to load fields conf", ex);
    }

  }

  public static ReportFieldConfig getFieldConfig(String name) {
    return fieldConfigMap.get(name);
  }

}
