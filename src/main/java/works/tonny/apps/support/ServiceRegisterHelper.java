/**
 * 
 */
package works.tonny.apps.support;

import javax.servlet.ServletContext;

/**
 * 服务管理类
 * 
 * @author 祥栋
 * @date 2012-12-4
 * @version 1.0.0
 */
public class ServiceRegisterHelper {

	/**
	 * 注册spring服务类
	 * 
	 * @param context
	 */
	public static void initSpring(ServletContext context) {
		ServiceManager.init(context);
	}

}
