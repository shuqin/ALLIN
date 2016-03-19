package cc.lovesq.base;

/**
 * User: john Date: 2010-4-26 Time: 21:25:59
 */
public class Info {
	public final static Integer OK = 1;

	public final static Integer FAILURE = 0;

	private boolean ok;

	private String message;

	public Info(boolean isOk) {
		this.ok = isOk;
	}

	public Info(boolean isOk, String message) {
		this.ok = isOk;
		this.message = message;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}