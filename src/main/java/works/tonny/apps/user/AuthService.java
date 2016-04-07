package works.tonny.apps.user;

import java.util.List;

import works.tonny.apps.user.model.LoginedMember;
import works.tonny.apps.user.service.LoginInterceptor;

public interface AuthService {

	/**
	 * 用户登录
	 * 
	 * @param username
	 *            用户登录名
	 * @param password
	 *            用户密码
	 * @return 用户，登录失败为空
	 */
	LoginedUser login(String username, String password);

	/**
	 * 会员登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	LoginedMember loginAsMember(String username, String password);

	/**
	 * 用户登录
	 * 
	 * @param username
	 *            用户登录名
	 * @param password
	 *            用户密码
	 * @return 用户，登录失败为空
	 */
	LoginedUser login(LoginInterceptor loginedInfo, String password);

	/**
	 * 用户登录
	 * 
	 * @param member
	 *            用户登
	 */
	void loginOut(User user);

	/**
	 * 用户权限
	 * 
	 * @Title: userPrivilege
	 * @param id
	 * @return
	 * @date 2011-11-30 下午12:18:07
	 * @author tonny
	 * @version 1.0
	 */
	List<Privilege> userPrivilege(String id);

	/**
	 * 角色权限
	 * 
	 * @Title: userPrivilege
	 * @param id
	 * @return
	 * @date 2011-11-30 下午12:18:07
	 * @author tonny
	 * @version 1.0
	 */
	List<Privilege> rolePrivilege(String id);

}
