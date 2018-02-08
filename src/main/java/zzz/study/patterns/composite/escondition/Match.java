package zzz.study.patterns.composite.escondition;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by shuqin on 17/12/19.
 */
@Data
public class Match implements Serializable {

  private static final long serialVersionUID = 2474940921739537720L;

  /** 要匹配的模糊关键字，比如商品名称的一部分 */
  private String query;

  /** 要匹配的百分比，比如 90% */
  private String minimumShouldMatch;

  public Match() {}

  public Match(String query, String minimumShouldMatch) {
    this.query = query;
    this.minimumShouldMatch = minimumShouldMatch;
  }
}
