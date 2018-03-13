package zzz.study.tech.batchcall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.Data;

/**
 * Created by shuqin on 18/3/13.
 *
 * 批量Http-Rest接口参数
 */
@Data
public class BatchHttpRestParam<T> {

  private String url;
  private List<T> keys;
  private Function<List<T>, Map> paramBuilderFunc;

  public BatchHttpRestParam() {
    this.keys = new ArrayList<>();
  }

  public BatchHttpRestParam(String url, List<T> keys,
                            Function<List<T>, Map> paramBuilderFunc) {
    this.url = url;
    this.keys = keys;
    this.paramBuilderFunc = paramBuilderFunc;
  }
}
