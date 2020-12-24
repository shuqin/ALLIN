package cc.lovesq.exception;

public enum Errors implements IError {

  ServerError(500, "internal error"),

  NoGoodsError(100000011, "No goods"),

  BookError(100000012, "下单失败"),

  ;

  ;

  private int code;
  private String message;

  Errors(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public boolean isSuccess() {
    return false;
  }
}
