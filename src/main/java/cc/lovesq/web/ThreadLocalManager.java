package cc.lovesq.web;

/**
 * ThreadLocal管理器
 * 
 */
public class ThreadLocalManager {
	private static final ThreadLocal<LoginUser> manager = new ThreadLocal<LoginUser>();

	public static void setLoginUser(LoginUser userObj) {
		LoginUser user = getLoginUser();
		if (user != null) {
			manager.remove();
		}
		manager.set(userObj);
	}

	public static LoginUser getLoginUser() {
		return manager.get();
	}

	public static void clear() {
		manager.remove();
	}
}