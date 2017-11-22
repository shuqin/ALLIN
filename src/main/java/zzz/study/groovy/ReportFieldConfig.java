package zzz.study.groovy;

import lombok.Data;

/**
 * Created by shuqin on 17/11/22.
 */
@Data
public class ReportFieldConfig {

  /** 报表字段标识 */
  private String name;

  /** 报表字段标题 */
  private String title;

  /** 报表字段逻辑脚本 */
  private String script;

}
