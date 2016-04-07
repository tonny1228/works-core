package works.tonny.apps.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 异常捕捉处理类
 * 
 * @author tonny
 */
public class ExceptionInterceptor extends AbstractInterceptor {
	protected final Logger log = LogFactory.getLogger(getClass());

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		try {
			return actionInvocation.invoke();
		} catch (Exception e) {
			log.error(actionInvocation.getProxy().getActionName() + " " + e.getMessage(), e);
			throw findCauseException(e);
		}
	}

	/**
	 * @param e
	 * @return
	 */
	private Exception findCauseException(Exception e) {
		if (e instanceof UndeclaredThrowableException) {
			UndeclaredThrowableException e1 = (UndeclaredThrowableException) e;
			if (e1.getUndeclaredThrowable() instanceof Exception)
				return findCauseException((Exception) e1.getUndeclaredThrowable());
			else
				return e;
		}
		if (e instanceof InvocationTargetException) {
			InvocationTargetException e1 = (InvocationTargetException) e;
			if (e1.getTargetException() instanceof Exception)
				return findCauseException((Exception) e1.getTargetException());
			else
				return e;
		}
		return e;
	}
}
