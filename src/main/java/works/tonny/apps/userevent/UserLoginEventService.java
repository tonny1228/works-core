package works.tonny.apps.userevent;

import javax.servlet.http.HttpServletRequest;

import works.tonny.apps.user.AbstractUser;

/**
 * 用户登录服务接口
 * 
 * @author tonny
 *
 */
public interface UserLoginEventService {
	/**
	 * 保存用户登录信息
	 * 
	 * @param user
	 * @return
	 */
	String login(AbstractUser user);

	/**
	 * 保存用户登录信息
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	String login(AbstractUser user, HttpServletRequest request);

	/**
	 * 退出
	 * 
	 * @param timeout
	 *            是否超时退出
	 * 
	 * @param user
	 *            登录用户信息
	 * @param logId
	 *            登录日志id
	 */
	void logout(AbstractUser user, String logId, boolean timeout);

	/**
	 * 创建查询
	 * 
	 * @return
	 */
	UserLoginEventQuery createQuery();
}
