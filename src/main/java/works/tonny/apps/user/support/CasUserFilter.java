package works.tonny.apps.user.support;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AuthService;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.User;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.web.LoginAction;

/**
 * 为cas登录的用户提供授权服务
 * 
 * @author tonny
 */
public class CasUserFilter implements Filter {

	/**
	 * 权限服务
	 */
	private AuthService authService;
	/**
	 * 权限服务
	 */
	private UserService userService;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		if (authService == null) {
			authService = ServiceManager.lookup("authService");
			userService = ServiceManager.lookup("userService");
		}
		if (session.getAttribute(LoginAction.USER_SESSION) == null) {
			String username = (String) session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
			User u = userService.getUserByUsername(username);
			LoginedUser user = authService.login(username, u.getPassword());
			session.setAttribute(LoginAction.USER_SESSION, user);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
