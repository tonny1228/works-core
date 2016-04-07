package works.tonny.apps.user.web;


import works.tonny.apps.support.CommonAction;
import works.tonny.apps.user.AuthedAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class MemberAuthInterceptor extends AbstractInterceptor {

	/**
	 * 判断用户是否登录，没有登录则返回登页面
	 */
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext context = actionInvocation.getInvocationContext();
		if (actionInvocation.getAction() instanceof AuthedAction) {
			((AuthedAction) actionInvocation.getAction()).setUrl();
		}
		if (context.getSession().containsKey(MemberAction.USER_SESSION)) {
			return actionInvocation.invoke();
		}
		return CommonAction.LOGIN;
	}

}
