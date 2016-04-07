package works.tonny.apps.user.service;

import java.util.EventListener;

import works.tonny.apps.exception.AuthException;
import works.tonny.apps.user.User;

/**
 * 用户登录成功后接口
 * 
 * @author tonny
 * 
 */
@Deprecated
public interface UserLoginedListener extends EventListener {
	/**
	 * 用户登录事件监听
	 * 
	 * @param user
	 * @throws AuthException
	 */
	void logined(User user) throws AuthException;
}
