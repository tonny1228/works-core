/**  
 * @Title: AnonymousTag.java
 * @Package works.tonny.user.web
 * @author Tonny
 * @date 2012-6-25 上午11:29:41
 */
package works.tonny.apps.user;

import javax.servlet.http.HttpSession;

import org.llama.library.utils.ThreadLocalMap;

import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.model.LoginedMember;
import works.tonny.apps.user.web.LoginAction;
import works.tonny.apps.user.web.MemberAction;

/**
 * @ClassName: AnonymousTag
 * @Description:
 * @author Tonny
 * @date 2012-6-25 上午11:29:41
 * @version 1.0
 */
public abstract class AnonymousTag extends AuthedTag {

	/**
	 * @return
	 * @see works.tonny.user.zxtx.apps.web.AuthedTag#loginedUser()
	 */
	@Override
	protected LoginedUser loginedUser() {
		HttpSession session = pageContext.getSession();
		LoginedUser user = (LoginedUser) session.getAttribute(LoginAction.USER_SESSION);
		LoginedMember member = (LoginedMember) session.getAttribute(MemberAction.USER_SESSION);
		if (user == null && member == null) {
			AuthService authService = ServiceManager.lookup(ServiceManager.AUTH_SERVICE);
			user = authService.login(User.ANONYMOUS.getUsername(), User.ANONYMOUS.getPassword());
			session.setAttribute(LoginAction.USER_SESSION, user);
			ThreadLocalMap.getInstance().putObject(LoginedUser.class, user);
		}
		return user == null ? user : member;
	}

}
