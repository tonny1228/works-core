package works.tonny.apps.user;

import javax.servlet.http.HttpSession;

import works.tonny.apps.user.web.LoginAction;
import works.tonny.apps.web.CommonTag;

public abstract class AuthedTag extends CommonTag {

	/**
	 * 当前用户
	 * 
	 * @return
	 */
	protected LoginedUser loginedUser() {
		HttpSession session = pageContext.getSession();
		LoginedUser user = (LoginedUser) session.getAttribute(LoginAction.USER_SESSION);
		return user;
	}
}
