/**
 * 
 */
package works.tonny.apps.support;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import works.tonny.apps.exception.NoSuchServiceException;

/**
 * 服务查询，通过名称查找服务并返回实例。没有服务排除异常
 * 
 * @author tonny
 * @date 2012-10-9
 * @version 1.0.0
 */
public class ServiceManager {

	public static final String USER_SERVICE = "userService";

	public static final String MAIL_SERVICE = "mailService";

	public static final String AUTH_SERVICE = "authService";

	public static final String TASK_SERVICE = "taskService";

	public static final String DATADIC_SERVICE = "dataDictionaryService";

	public static final String MASP_SERVICE = "maspService";

	public static final String HTTP_SERVICE = "httpService";

	public static final String LOG_SERVICE = "logService";

	public static final String FORM_SERVICE = "formService";

	public static final String LAZY_SERVICE = "entityLazyProxyService";

	/**
	 * 
	 */
	public static final String ATTACH_REFERENCE_SERVICE = "attachReferenceService";

	/**
	 * 
	 */
	public static final String ATTACH_SERVICE = "attachService";

	/**
	 * 
	 */
	public static final String CATALOG_SERVICE = "catalogService";

	/**
	 * 
	 */
	public static final String ROLE_SERVICE = "roleService";

	/**
	 * 
	 */
	public static final String MEMBER_SERVICE = "memberService";

	/**
	 * 
	 */
	public static final String POSITION_SERVICE = "positionService";

	private static ApplicationContext context;

	private static ServletContext servletContext;

	/**
	 * 初始化context
	 * 
	 * @param context
	 */
	static void init(ApplicationContext context) {
		ServiceManager.context = context;
	}

	/**
	 * 初始化context
	 * 
	 * @param context
	 */
	static void init(ServletContext context) {
		ServiceManager.servletContext = context;
	}

	/**
	 * 查找服务
	 * 
	 * @param serviceName 服务名称
	 * @return
	 */
	public static <T> T lookup(String serviceName) throws NoSuchServiceException {
		return (T) getApplicationContext().getBean(serviceName);
	}

	/**
	 * 查找服务
	 * 
	 * @param serviceName 服务名称
	 * @return
	 */
	public static <T> T lookup(Class clazz) throws NoSuchServiceException {
		String[] beanNames = getApplicationContext().getBeanNamesForType(clazz);
		if (beanNames == null || beanNames.length == 0) {
			throw new NoSuchServiceException(clazz + "的服务没有注册");
		}
//		if (beanNames.length > 1) {
//			throw new NoSuchServiceException(clazz + "的服务无法从中匹配[" + StringUtils.join(beanNames, ",") + "]");
//		}

		return (T) getApplicationContext().getBean(beanNames[0]);
	}

	/**
	 * 读取ApplicationContext，为空去初始化
	 * 
	 * @return
	 */
	static ApplicationContext getApplicationContext() {
		if (context == null && servletContext != null) {
			context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		}
		if (context == null) {
			throw new IllegalStateException("没有初始化");
		}
		return context;
	}

}
