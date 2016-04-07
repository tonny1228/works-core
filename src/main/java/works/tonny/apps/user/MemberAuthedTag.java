package works.tonny.apps.user;

import javax.servlet.http.HttpSession;

import works.tonny.apps.user.web.MemberAction;
import works.tonny.apps.web.CommonTag;

public abstract class MemberAuthedTag extends CommonTag {

	/**
	 * 当前用户
	 * 
	 * @return
	 */
	protected LoginedUser loginedUser() {
		HttpSession session = pageContext.getSession();
		LoginedUser user = (LoginedUser) session.getAttribute(MemberAction.USER_SESSION);
		return user;
	}
}
