package cc.lovesq.exception;

public interface IError {

  int getCode();
  String getMessage();
  boolean isSuccess();

}

