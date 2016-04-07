/**  
 * @Title: AuthVerifyInterceptor.java
 * @Package works.tonny.user.service
 * @author Tonny
 * @date 2011-11-23 下午3:38:14
 */
package works.tonny.apps.support;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

import works.tonny.apps.auth.AuthVerify;
import works.tonny.apps.user.AuthedAbstractService;

/**
 * 权限验证拦截器
 * 
 * @ClassName: AuthVerifyInterceptor
 * @Description:
 * @author Tonny
 * @date 2011-11-23 下午3:38:14
 * @version 1.0
 */
public class AuthVerifyInterceptor implements IntroductionInterceptor {

	/*
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
//		if (methodInvocation.getThis() instanceof AuthedAbstractService) {
//			AuthedAbstractService executor = (AuthedAbstractService) methodInvocation.getThis();
			AuthVerify support = method.getAnnotation(AuthVerify.class);
			if (support != null) {
				AuthedAbstractService.validateAuth(method.getDeclaringClass().getSimpleName() + "." + support.value().name().toLowerCase());
			}
//		}
		Object[] args = methodInvocation.getArguments();
		return method.invoke(methodInvocation.getThis(), args);
	}

	/*
	 * @see
	 * org.springframework.aop.DynamicIntroductionAdvice#implementsInterface
	 * (java.lang.Class)
	 */
	public boolean implementsInterface(Class intf) {
		return intf.isInterface();
	}

}
