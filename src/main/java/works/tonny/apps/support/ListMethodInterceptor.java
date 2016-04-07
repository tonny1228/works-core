/**  
 * @Title: ListMethodInterceptor.java
 * @Package works.tonny.framework.dao
 * @author Tonny
 * @date 2011-10-15 下午4:22:18
 */
package works.tonny.apps.support;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.llama.library.utils.PagedList;
import org.springframework.aop.IntroductionInterceptor;

/**
 * @ClassName: ListMethodInterceptor
 * @Description:
 * @author Tonny
 * @date 2011-10-15 下午4:22:18
 * @version 1.0
 */
public class ListMethodInterceptor implements IntroductionInterceptor {

	/*
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		BaseDAOSupport<?> executor = (BaseDAOSupport<?>) methodInvocation.getThis();
		Class returnType = methodInvocation.getMethod().getReturnType();
		ListSupport support = methodInvocation.getMethod().getAnnotation(ListSupport.class);
		Object[] args = methodInvocation.getArguments();

		if (support != null && returnType.equals(PagedList.class)) {
			if (!support.offsetSupport())
				return executor.find(support.field(), ArrayUtils.subarray(args, 0, args.length - 2),
						support.connector(), support.operator(), support.order(), support.defaults(),
						(Integer) args[args.length - 2], (Integer) args[args.length - 1]);
			else
				return executor.list(support.field(), ArrayUtils.subarray(args, 0, args.length - 2), support.connector(),
						support.operator(), support.order(), support.defaults(), (Integer) args[args.length - 2],
						(Integer) args[args.length - 1]);
		} else if (support != null) {
			List list = executor.find(support.field(), args, support.connector(), support.operator(), support.order(),
					support.defaults());
			if (methodInvocation.getMethod().getReturnType().equals(List.class)) {
				return list;
			}
			if (list == null || list.size() == 0 || list.get(0) == null) {
				if (methodInvocation.getMethod().getReturnType().equals(boolean.class)) {
					return false;
				}
				if (methodInvocation.getMethod().getReturnType().equals(void.class)) {
					return null;
				}
				if (methodInvocation.getMethod().getReturnType().isPrimitive()) {
					return 0;
				} else {
					return null;
				}
			}
			return list.get(0);
		}

		SQLMappingListSupport mappingListSupport = methodInvocation.getMethod().getAnnotation(
				SQLMappingListSupport.class);
		if (mappingListSupport != null && returnType.equals(PagedList.class)) {
			if (!mappingListSupport.offsetSupport())
				return executor.find(mappingListSupport.name(), mappingListSupport.appender(),
						ArrayUtils.subarray(args, 0, args.length - 2), mappingListSupport.paramNames(),
						(Integer) args[args.length - 2], (Integer) args[args.length - 1]);
			else
				return executor.list(mappingListSupport.name(), mappingListSupport.appender(),
						ArrayUtils.subarray(args, 0, args.length - 2), mappingListSupport.paramNames(),
						(Integer) args[args.length - 2], (Integer) args[args.length - 1]);
		} else if (mappingListSupport != null) {
			List list = executor.find(mappingListSupport.name(), mappingListSupport.appender(), args,
					mappingListSupport.paramNames());
			if (methodInvocation.getMethod().getReturnType().equals(List.class)) {
				return list;
			}
			if (list == null || list.size() == 0 || list.get(0) == null) {
				if (methodInvocation.getMethod().getReturnType().equals(boolean.class)) {
					return false;
				}
				if (methodInvocation.getMethod().getReturnType().equals(void.class)) {
					return null;
				}
				if (methodInvocation.getMethod().getReturnType().isPrimitive()) {
					return 0;
				} else {
					return null;
				}
			}
			return list.get(0);
		}
		Method method = BaseDAOSupport.class.getDeclaredMethod(methodInvocation.getMethod().getName(), methodInvocation
				.getMethod().getParameterTypes());
		return method.invoke(executor, args);
	}

	/*
	 * @see
	 * org.springframework.aop.DynamicIntroductionAdvice#implementsInterface
	 * (java.lang.Class)
	 */
	public boolean implementsInterface(Class intf) {
		return intf.isInterface() && BaseDAOSupport.class.isAssignableFrom(intf);
	}

}
