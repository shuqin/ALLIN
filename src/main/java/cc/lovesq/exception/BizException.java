package cc.lovesq.exception;

/**
 * 业务系统异常类,需要进行捕获处理
 */
public class BizException extends Exception {

	private static final long serialVersionUID = 1L;

	protected Integer code;

	protected String message;
	
	private IErrorCode errorCode;

	public BizException(IErrorCode resultMessage, String message, Throwable e) {
		this.code = resultMessage.getCode();
		this.message = (message != null ? message : resultMessage.getMessage());
		this.errorCode=resultMessage;
	}

	public BizException(IErrorCode resultMessage) {
		this(resultMessage, null, null);
	}

	public BizException(IErrorCode resultMessage, String message) {
		this(resultMessage, message, null);
	}

	public BizException(IErrorCode resultMessage, Throwable e) {
		this(resultMessage, null, e);
	}
	
	public Integer getCode() {
		return code;
	}
	@Override
    public String getMessage() {
		return message;
	}

	public IErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(IErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	
}
