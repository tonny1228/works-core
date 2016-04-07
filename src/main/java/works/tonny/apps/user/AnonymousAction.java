package works.tonny.apps.user;

import org.llama.library.utils.ThreadLocalMap;
import works.tonny.apps.exception.AuthException;
import works.tonny.apps.support.CommonAction;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.model.LoginedMember;
import works.tonny.apps.user.web.LoginAction;
import works.tonny.apps.user.web.MemberAction;

import java.util.Map;

/**
 * 通用struts2 action，实现取request和session
 * 
 * @author 刘祥栋
 */
public class AnonymousAction extends CommonAction {

	/**
	 * 获得当前登录用户
	 * 
	 * @return
	 * @throws AuthException
	 *             用户没有登录则抛出异常
	 */
	protected LoginedUser loginedUser() {
		if (session == null) {
			return null;
		}

		LoginedUser user = (LoginedUser) session.get(LoginAction.USER_SESSION);
		LoginedMember member = (LoginedMember) session.get(MemberAction.USER_SESSION);
		if (user == null && member == null) {
			AuthService authService = ServiceManager.lookup(ServiceManager.AUTH_SERVICE);
			user = authService.login(User.ANONYMOUS.getUsername(), User.ANONYMOUS.getPassword());
			request.getSession().setAttribute(LoginAction.USER_SESSION, user);
			ThreadLocalMap.getInstance().putObject(LoginedUser.class, user);
		}
		return user != null ? user : member;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		super.setSession(session);
		loginedUser();
	}

}
