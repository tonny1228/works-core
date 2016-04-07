/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-18 上午9:52:13
 * @author tonny
 */
package works.tonny.apps.support;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * <p>
 * 自定义转向页面拦截器，可以指向到工程中的自定义路径。
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class CustomPageInterceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if (!(actionInvocation.getAction() instanceof CommonAction)) {
			return actionInvocation.invoke();
		}
		CommonAction action = (CommonAction) actionInvocation.getAction();
		action.makeCustomPath();
		return actionInvocation.invoke();
	}
}