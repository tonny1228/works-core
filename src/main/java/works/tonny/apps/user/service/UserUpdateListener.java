package works.tonny.apps.user.service;

import java.util.EventListener;

import works.tonny.apps.user.User;

/**
 * 用户更新监听器，在新建用户、用户信息更新、用户删除后进行数据初始化
 * 
 * @author tonny
 */
@Deprecated
public interface UserUpdateListener extends EventListener {
	/**
	 * 用户创建监听
	 * 
	 * @param user 用户
	 */
	void userSaved(User user);

	/**
	 * 用户更新监听
	 * 
	 * @param user 用户
	 */
	void userUpdated(User user);

	/**
	 * 用户删除监听
	 * 
	 * @param user 用户
	 */
	void userDeleted(User user);
}
