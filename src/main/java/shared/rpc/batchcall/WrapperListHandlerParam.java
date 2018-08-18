package shared.rpc.batchcall;

import java.util.List;
import java.util.function.Function;

import lombok.Data;

/**
 * Created by shuqin on 18/3/13.
 */
@Data
public class WrapperListHandlerParam<T,R> {

  private List<T> keys;
  private Function<WrapperListHandlerParam, List<R>> handleBizDataFunc;

  public WrapperListHandlerParam(List<T> keys,
                                 Function<WrapperListHandlerParam, List<R>> handleBizDataFunc) {
    this.keys = keys;
    this.handleBizDataFunc = handleBizDataFunc;
  }
}
