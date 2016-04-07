/**  
 * @Title: AuthVerifyAdvisor.java
 * @Package works.tonny.user.service
 * @author Tonny
 * @date 2011-11-23 下午3:40:20
 */
package works.tonny.apps.support;

import org.springframework.aop.support.DefaultIntroductionAdvisor;


/**
 * 权限验证拦截指导
 * 
 * @ClassName: AuthVerifyAdvisor
 * @Description:
 * @author Tonny
 * @date 2011-11-23 下午3:40:20
 * @version 1.0
 */
public class AuthVerifyAdvisor extends DefaultIntroductionAdvisor {
	/**
	 * <p>
	 * </p>
	 */
	public AuthVerifyAdvisor() {
		super(new AuthVerifyInterceptor());
	}
}
