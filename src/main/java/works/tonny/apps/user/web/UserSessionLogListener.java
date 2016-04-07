package works.tonny.apps.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.userevent.UserLoginEventService;

/**
 * 监听用户登录、登出会话，并记录日志
 * 
 * @author tonny
 *
 */
public class UserSessionLogListener implements HttpSessionBindingListener {

	private LoginedUser user;

	private HttpServletRequest request;

	private UserLoginEventService userLoginEventService;

	private String id;

	private boolean timeout = true;

	public UserSessionLogListener(LoginedUser user, HttpServletRequest request,
			UserLoginEventService userLoginEventService) {
		super();
		this.user = user;
		this.request = request;
		this.userLoginEventService = userLoginEventService;
	}

	@Override
	/**
	 * 绑定时登录
	 */
	public void valueBound(HttpSessionBindingEvent event) {
		id = userLoginEventService.login(user.getUser(), request);
		request = null;
	}

	@Override
	/**
	 * 解绑时退出
	 */
	public void valueUnbound(HttpSessionBindingEvent event) {
		userLoginEventService.logout(user.getUser(), id, timeout);
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

}