/**
 * 
 */
package works.tonny.apps.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.llama.library.utils.ThreadLocalMap;

import works.tonny.apps.deploy.SessionFactoryManager;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.model.LoginedMember;
import works.tonny.apps.user.web.LoginAction;
import works.tonny.apps.user.web.MemberAction;

/**
 * @author 祥栋
 * @date 2012-11-15
 * @version 1.0.0
 */
@WebFilter(filterName = "threadlocal", asyncSupported = true, urlPatterns = { "*.action", "*.jsp", "/servlet/*" }, servletNames = "filedown")
public class ThreadContextFilter implements Filter {

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		ThreadLocalMap map = ThreadLocalMap.getInstance();
		map.init();
		HttpServletRequest request = (HttpServletRequest) req;
		Enumeration<String> names = request.getSession().getAttributeNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			map.putObject(name, request.getSession().getAttribute(name));
		}
		LoginedUser user = (LoginedUser) request.getSession().getAttribute(LoginAction.USER_SESSION);
		if (user != null) {
			map.putObject(LoginedUser.class, user);
			map.putObject(SessionFactoryManager.SCHEMA, user.getSchema());
		} else {
			LoginedMember member = (LoginedMember) request.getSession().getAttribute(MemberAction.USER_SESSION);
			map.putObject(LoginedUser.class, member);
		}
		chain.doFilter(req, res);
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {

	}
}
