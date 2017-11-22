package zzz.study.groovy;

import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shuqin on 17/11/23.
 */
public class YamlConfigDirLoader {

  private String dir;

  public YamlConfigDirLoader(String dir) {
    this.dir = dir;
  }

  public List<ReportFieldConfig> loadConfigs() {
    File[] files = new File(dir).listFiles();
    return Arrays.stream(files).map(
        file -> {
          try {
            String
                content =
                StreamUtils.copyToString(new FileInputStream(file), Charset.forName("utf-8"));
            return YamlConfigLoader.loadConfig(content);
          } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
          }
        }
    ).collect(Collectors.toList());
  }

}
