package zzz.study.algorithm.object;

import lombok.Data;

@Data
public class TableField {

  String tablename;
  String field;
  String id;

  public TableField(String tablename, String field, String id) {
    this.tablename = tablename;
    this.field = field;
    this.id = id;
  }

  public static TableField buildFrom(String combined) {
    String[] parts = combined.split(":");
    if (parts != null && parts.length == 3) {
      return new TableField(parts[0], parts[1], parts[2]);
    }
    throw new IllegalArgumentException(combined);
  }

}
