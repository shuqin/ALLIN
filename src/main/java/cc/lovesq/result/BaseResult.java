package cc.lovesq.result;

import cc.lovesq.exception.IError;
import lombok.Data;

/**
 * Created by shuqin on 16/10/24.
 */
@Data
public class BaseResult<T> {

  public static final String successMsg = "successful";
  public static final Integer successCode = 200;

  private int code = successCode;
  private String msg = successMsg;
  private boolean isSuccess = true;
  private T data;

  public static BaseResult succ(String customizedMessage) {
    BaseResult result = new BaseResult();
    result.setMsg(customizedMessage);
    return result;
  }

  public static <T> BaseResult succ(T data) {
    BaseResult result = new BaseResult();
    result.setData(data);
    return result;
  }

  /**
   * 返回的错误必须从 IError 里获取，强制错误码集中管理
   */
  public static BaseResult failed(IError error) {
    BaseResult result = new BaseResult();
    result.setCode(error.getCode());
    result.setMsg(error.getMessage());
    result.setSuccess(false);
    return result;
  }

}
