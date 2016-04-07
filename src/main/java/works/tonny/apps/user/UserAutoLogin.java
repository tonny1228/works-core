package works.tonny.apps.user;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户自动登录接口
 * 
 * @author tonny
 *
 */
public interface UserAutoLogin {

	/**
	 * 从request信息中获取信息为用户登录
	 * 
	 * @param request
	 * @return
	 */
	LoginedUser login(HttpServletRequest request);

}
