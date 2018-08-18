package cc.lovesq.exception;

/**
 * 业务系统异常类
 */
public class BizException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  protected Integer code;
  protected String message;

  public BizException(IError error) {
    this(error, null);
  }

  public BizException(IError error, String message) {
    this.code = error.getCode();
    String errInfo = error.getMessage();
    this.message = (message != null ? String.format(errInfo, message) : errInfo);
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
