package zzz.study.apidesign.export.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ExportParam implements Serializable {

  /** 调用方，必传 */
  private String source;

  /** 导出业务类型，必传 */
  private String bizType;

  /** 导出业务细分，必传 */
  private String category;

  /** 导出策略，默认 */
  private String strategy = "standard";

  /** 搜索参数，必传 */
  private SearchParam search;

  /** 请求ID，必传 */
  private String requestId;


  /** 导出选项，可用于定制化 */
  private Map<String, Object> options;

  /** 导出额外信息 */
  private Map<String, String> extra;

}



