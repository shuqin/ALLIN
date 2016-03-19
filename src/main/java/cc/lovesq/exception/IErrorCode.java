package cc.lovesq.exception;

public interface IErrorCode {
	
	public int getCode();

	
	public String getMessage();

	public static final IErrorCode SUCCESSFUL = new IErrorCode() {
		public int getCode() {
			return 200;
		}

		public String getMessage() {
			return "success";
		}
	};
	
	public static final IErrorCode UNFINISHED = new IErrorCode() {
		public int getCode() {
			return 201;
		}

		public String getMessage() {
			return "unfinished";
		}
	};
	
	public static final IErrorCode FAILED = new IErrorCode() {
		public int getCode() {
			return 0;
		}

		public String getMessage() {
			return "failed";
		}
	};
	
}

