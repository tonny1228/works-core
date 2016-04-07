/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-1-4 上午11:50:05
 * @author tonny
 */
package works.tonny.apps.user.service;

/**
 * <p>
 * 登录拦截器
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface LoginInterceptor {
	/**
	 * 获取用户的库
	 * 
	 * @return
	 * @author tonny
	 */
	String getSchema();

	/**
	 * 获取用户的登录名
	 * 
	 * @return
	 * @author tonny
	 */
	String getUsername();
}