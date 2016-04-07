/**
 * 
 */
package works.tonny.apps.support;

import org.llama.library.cache.Cache;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author 祥栋
 */
public class CacheInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final String SUCCESS = "success";

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Cache cache = ServiceManager.lookup(Cache.class);
		if (!(invocation.getAction() instanceof CommonAction)) {
			return invocation.invoke();
		}
		CommonAction action = (CommonAction) invocation.getAction();
		if (!"get".equalsIgnoreCase(action.getRequest().getMethod())) {
			return invocation.invoke();
		}

		String url = action.getRequest().getRequestURL().toString() + "?" + action.getRequest().getQueryString();
		action.getRequest().setAttribute("_cache.key", url);
		Object fromCache = cache.getFromCache(url);
		if (fromCache == null) {
			return invocation.invoke();
		}

		return SUCCESS;
	}
}
