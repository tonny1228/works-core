/**  
 * @Title: LoginAction.java
 * @Package works.tonny.user.web
 * @author Tonny
 * @date 2011-11-8 下午2:43:20
 */
package works.tonny.apps.user.web;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.support.CommonAction;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.service.AuthEntityService;
import works.tonny.apps.user.service.impl.DefaultLoginInterceptor;
import works.tonny.apps.userevent.UserLoginEventService;

/**
 * @ClassName: LoginAction
 * @Description:
 * @author Tonny
 * @date 2011-11-8 下午2:43:20
 * @version 1.0
 */
public class LoginAction extends CommonAction {

	private static final String SESSION_LISTENER = "xxxxxxxxxxxxxx";

	/**
	 * 用户登录失败6次后锁定分钟
	 */
	private static final int LOCK_MINUTES = 2;

	public static final String USER_SESSION = "logined_user";
	/**
	 * 用户服务
	 */
	private UserService userService;

	/**
	 * 权限服务
	 */
	private AuthEntityService authService;

	private String username;

	private String password;

	private String theme;

	private String url;

	private UserLoginEventService userLoginEventService;

	/**
	 * 用户登录与信息初始化
	 * 
	 * @return
	 */
	public String login() {
		if (StringUtils.isEmpty(username)) {
			return LOGIN;
		}
		this.clearActionErrors();
		this.clearFieldErrors();
		// Object sessCode = session.get(VERIFY_CODE);
		// if (sessCode == null || !sessCode.equals(verify)) {
		// this.addFieldError("error.user.login.verify", getText(
		// "action.user.login.verify", "验证码不一致"));
		// return INPUT;
		// }
		// request.getSession().removeAttribute(VERIFY_CODE);
		/**
		 * 用户登录失败6次后5分钟不允许登录
		 */
		if (theme != null) {
			request.getSession().setAttribute("_view.theme", theme);
		}
		int loginCount = 0;
		long loginTime = 0;
		if (request.getSession().getAttribute("_count_" + username) != null) {
			loginCount = ((Integer) request.getSession().getAttribute("_count_" + username)).intValue();
		}

		if (request.getSession().getAttribute("_date_" + username) != null) {
			loginTime = ((Long) request.getSession().getAttribute("_date_" + username)).longValue();
			long current = System.currentTimeMillis();
			if (current - loginTime < 1000 * 60 * LOCK_MINUTES) {
				this.addActionError(getText("action.user.locked", "用户账号被锁定，$1秒后再试",
						new String[] { String.valueOf(60 * LOCK_MINUTES - (current - loginTime) / 1000) }));
				return INPUT;
			} else {
				request.getSession().removeAttribute("_date_" + username);
				loginCount = 0;
			}
		}

		if (loginCount >= 6) {
			request.getSession().setAttribute("_date_" + username, System.currentTimeMillis());
			this.addActionError(getText("action.user.locked", "用户账号被锁定，$1分钟后再试",
					new String[] { String.valueOf(LOCK_MINUTES) }));
			return INPUT;
		}

		try {
			LoginedUser user = authService.login(new DefaultLoginInterceptor(username), password);
			request.getSession().invalidate();
			request.getSession().setAttribute(USER_SESSION, user);
			request.getSession().setAttribute(SESSION_LISTENER,
					new UserSessionLogListener(user, request, userLoginEventService));
		} catch (Exception e) {
			log.error(e);
			this.addActionError(getText("user.notallowedlogin", new String[] { getText(e.getCause().getCause()
					.getMessage()) }));
			return "login";
		}
		if (StringUtils.isEmpty(url)) {
			return "main";
		} else {
			return "forward";
		}
	}

	public String logout() {
		UserSessionLogListener listener = (UserSessionLogListener) request.getSession().getAttribute(SESSION_LISTENER);
		if (listener != null) {
			listener.setTimeout(false);
		}
		request.getSession().invalidate();
		//url = request.getHeader("referer");
		return SUCCESS;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthEntityService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthEntityService authService) {
		this.authService = authService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme
	 *            the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	public UserLoginEventService getUserLoginEventService() {
		return userLoginEventService;
	}

	public void setUserLoginEventService(UserLoginEventService userLoginEventService) {
		this.userLoginEventService = userLoginEventService;
	}

}
