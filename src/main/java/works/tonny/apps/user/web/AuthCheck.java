package works.tonny.apps.user.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;

import works.tonny.apps.exception.AuthException;
import works.tonny.apps.exception.WebException;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.web.CommonTag;

/**
 * 判断用户权限，有权限则返回一个可点击的对象
 * 
 * @author tonny
 * 
 */
public class AuthCheck extends CommonTag {
	/**
	 * 权限代码
	 */
	private String auth;

	protected Component component;

	private String errorMessage;

	private boolean hasAuth(String auth) {
		return loginedUser().hasAuth(auth);
		//return true;
		// try {
		// for (String api : loginedUser().getPrivileges()) {
		// if (api.equals(auth)) {
		// return true;
		// }
		// if (api.matches(auth.replaceAll("\\.\\*", ".[^.]*").replaceAll("\\.",
		// "\\\\."))) {
		// return true;
		// }
		// }
		// } catch (RuntimeException e) {
		// return false;
		// }
	}

	private LoginedUser loginedUser() {
		LoginedUser user = (LoginedUser) pageContext.getSession().getAttribute(LoginAction.USER_SESSION);
		if (user == null) {
			throw new AuthException("user");
		}
		return user;
	}

	public int doEndTag() throws JspException {
		try {

			if (!hasAuth(auth)) {
				pageContext.getOut().write(StringUtils.stripToEmpty(errorMessage));
				return SKIP_BODY;
			}
			pageContext.getOut().write(getBodyContent().getString());
		} catch (IOException e) {
			log.error("权限错误", e);
			throw new WebException(e);
		}

		return EVAL_PAGE;
	}

	/**
	 * 不处理任何动作
	 */
	public int doStartTag() throws JspException {
		if (!hasAuth(auth))
			return SKIP_BODY;
		return EVAL_PAGE;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
