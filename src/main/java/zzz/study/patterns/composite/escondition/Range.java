package zzz.study.patterns.composite.escondition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ES时间范围
 *
 * Created by shuqin on 17/6/5.
 */
public class Range implements Serializable {

  private static final long serialVersionUID = -5307875281175084597L;

  private long gte;
  private long lte;

  public Range(long gte, long lte){
    this.gte = gte;
    this.lte = lte;
  }

  public Range() {}

  public long getGte() {
    return gte;
  }

  public long getLte() {
    return lte;
  }

  public Map<String, Object> toMap(){
    Map<String, Object> rsMap = new HashMap<>();
    rsMap.put("gte", this.gte);
    rsMap.put("lte", this.lte);
    return rsMap;
  }
}
