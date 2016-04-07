package works.tonny.apps.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

import works.tonny.apps.Query;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 通用struts2 action，实现取request和session
 * 
 * @author 刘祥栋
 */
public class CommonAction extends ActionSupport implements ServletRequestAware, SessionAware, ServletContextAware,
		ServletResponseAware {
	public static final String LOGIN = "login";

	public static final String INPUT = "input";

	public static final String VIEW = "view";

	public static final String ERROR_MESSAGE = "error";

	protected final Logger log = LogFactory.getLogger(getClass());

	protected static ActionUtils utils = new ActionUtils();

	/**
	 * http request
	 */
	protected HttpServletRequest request;

	/**
	 * ServletContext
	 */
	protected ServletContext context;

	/**
	 * http session
	 */
	protected Map<String, Object> session;

	protected String forward;

	protected String pageName;

	protected String pagesizeName;

	protected HttpServletResponse response;

	protected EntityLazyProxy entityLazyProxy;

	protected String customPath;

	protected String customPattern;

	private int offset = 0;

	private int limit = 10;

	/**
	 * 查询定制化的路径，根据此路径查询定制化页面
	 */
	public void makeCustomPath() {
		if (StringUtils.isNotEmpty(customPath)) {
			return;
		}
		String u = request.getRequestURI();
		customPath = u.replaceAll(request.getContextPath() + customPattern, "$1");
		if (customPath.equals(u)) {
			customPath = "";
		}
	}

	protected String getParameterNullToEmpty(String name) {
		String[] parameterValues = request.getParameterValues(name);
		if (parameterValues != null && parameterValues.length > 1) {
			return StringUtils.join(parameterValues, ", ");
		} else
			return StringUtils.defaultString(request.getParameter(name), "");
	}

	protected String getParameter(String name) {
		String[] parameterValues = request.getParameterValues(name);
		if (parameterValues != null && parameterValues.length > 1) {
			return StringUtils.join(parameterValues, ", ");
		} else
			return request.getParameter(name);
	}

	/**
	 * 查询所有的高级查询条件
	 * 
	 * @param query
	 * @return
	 * @author tonny
	 */
	public List<QueryDesc> descQueryCriteria(Query query) {
		List<QueryDesc> list = new ArrayList<QueryDesc>();
		Class c = null;
		final Class<?>[] interfaces = query.getClass().getInterfaces();
		for (Class<?> class1 : interfaces) {
			if (ClassUtils.isAssignable(class1, Query.class)) {
				c = class1;
			}
		}
		final Method[] methods = c.getMethods();
		Method[] queryMethods = new Method[methods.length];
		for (Method method : methods) {
			final QueryCriteria annotation = method.getAnnotation(QueryCriteria.class);
			if (annotation == null) {
				continue;
			}
			queryMethods[annotation.order()] = method;

		}
		for (Method method : queryMethods) {
			if (method == null) {
				continue;
			}
			final QueryCriteria annotation = method.getAnnotation(QueryCriteria.class);
			String name = null;
			if (StringUtils.isEmpty(annotation.name())) {
				name = method.getDeclaringClass().getSimpleName() + "." + method.getName();
			} else {
				name = annotation.name();
			}
			QueryDesc desc = new QueryDesc(name, annotation.values(), annotation.type(), method);
			if (annotation.valueNames().length != annotation.values().length) {
				desc.setValueNames(annotation.values());
			} else {
				desc.setValueNames(annotation.valueNames());
			}
			addDesc(list, desc);
		}
		return list;
	}

	/**
	 * 查询所有的高级查询条件
	 * 
	 * @param query
	 * @return
	 * @author tonny
	 */
	public List<QueryDesc> descQueryOrder(Query query) {
		List<QueryDesc> list = new ArrayList<QueryDesc>();
		Class c = null;
		final Class<?>[] interfaces = query.getClass().getInterfaces();
		for (Class<?> class1 : interfaces) {
			if (ClassUtils.isAssignable(class1, Query.class)) {
				c = class1;
			}
		}
		final Method[] methods = c.getMethods();
		Method[] orderMethods = new Method[methods.length];
		for (Method method : methods) {
			final QueryOrder annotation = method.getAnnotation(QueryOrder.class);
			if (annotation == null) {
				continue;
			}
			orderMethods[annotation.order()] = method;

		}
		for (Method method : orderMethods) {
			if (method == null) {
				continue;
			}
			final QueryOrder annotation = method.getAnnotation(QueryOrder.class);
			String name = null;
			if (StringUtils.isEmpty(annotation.name())) {
				name = method.getDeclaringClass().getSimpleName() + "." + method.getName();
			} else {
				name = annotation.name();
			}
			if (method.getParameterTypes().length == 0) {
				addDesc(list, new QueryDesc(name, null, null, method));
			} else {
				addDesc(list, new QueryDesc(name + ".desc", null, null, method));
				addDesc(list, new QueryDesc(name + ".asc", null, null, method));
			}
		}
		return list;
	}

	private void addDesc(List list, Object obj) {
		list.add(obj);
	}

	protected int getIntParameter(String name) {
		return NumberUtils.toInt(request.getParameter(name));
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@Deprecated
	/**
	 * getOffset instead
	 * @return
	 */
	public int getPage() {
		return NumberUtils.toInt(request.getParameter(StringUtils.defaultString(pageName, "page")), 1);
	}

	public int getOffset() {
		if (pageName != null) {
			offset = NumberUtils.toInt(request.getParameter(pageName), offset);
		}
		if (offset == 0 && getPage() > 1) {
			return (getPage() - 1) * getPagesize();
		}
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Deprecated
	/**
	 * getLimit instead
	 * @return
	 */
	public int getPagesize() {
		return NumberUtils.toInt(request.getParameter(StringUtils.defaultString(pagesizeName, "pagesize")), 10);
	}

	public int getLimit() {
		if (pagesizeName != null) {
			limit = NumberUtils.toInt(request.getParameter(pagesizeName), limit);
		}
		if (limit == 10 && getPagesize() != 10) {
			return getPagesize();
		}
		return limit;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public void setPagesizeName(String pagesizeName) {
		this.pagesizeName = pagesizeName;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setError(String errorMessage) {
		request.setAttribute("error", errorMessage);
	}

	public String error(String errorMessage) {
		request.setAttribute("error", errorMessage);
		return ERROR;
	}

	/**
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public EntityLazyProxy getEntityLazyProxy() {
		return entityLazyProxy;
	}

	public void setEntityLazyProxy(EntityLazyProxy entityLazyProxy) {
		this.entityLazyProxy = entityLazyProxy;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * @return the customPath
	 */
	public String getCustomPath() {
		return customPath;
	}

	/**
	 * @param customPath
	 *            the customPath to set
	 */
	public void setCustomPath(String customPath) {
		this.customPath = customPath;
	}

	/**
	 * @return the customPattern
	 */
	public String getCustomPattern() {
		return customPattern;
	}

	/**
	 * @param customPattern
	 *            the customPattern to set
	 */
	public void setCustomPattern(String customPattern) {
		this.customPattern = customPattern;
	}

	public ActionUtils getUtils() {
		return utils;
	}

}
