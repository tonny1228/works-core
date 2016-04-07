/**  
 * @Title: MemberAuthedAction.java
 * @Package works.tonny.core.action
 * @author Tonny
 * @date 2012-7-22 下午12:16:24
 */
package works.tonny.apps.user;

import works.tonny.apps.user.model.LoginedMember;
import works.tonny.apps.user.web.MemberAction;

/**
 * @ClassName: MemberAuthedAction
 * @Description:
 * @author Tonny
 * @date 2012-7-22 下午12:16:24
 * @version 1.0
 */
public class MemberAuthedAction extends AuthedAction {

	private UserAutoLogin userAutoLogin;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return
	 * @see works.tonny.user.zxtx.apps.web.AuthedAction#loginedUser()
	 */
	@Override
	public LoginedMember loginedUser() {
		if (session == null) {
			return null;
		}
		LoginedMember user = (LoginedMember) session.get(MemberAction.USER_SESSION);
		if (user == null && userAutoLogin != null) {
			user = (LoginedMember) userAutoLogin.login(request);
		}
		return user;
	}

	public void setUserAutoLogin(UserAutoLogin userAutoLogin) {
		this.userAutoLogin = userAutoLogin;
	}
}
