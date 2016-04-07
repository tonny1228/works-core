/**  
 * @Title: AuthVerify.java
 * @Package works.tonny.user.service
 * @author Tonny
 * @date 2011-11-23 下午3:36:07
 */
package works.tonny.apps.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口的方法的权限验证
 * 
 * @ClassName: AuthVerify
 * @Description:
 * @author Tonny
 * @date 2011-11-23 下午3:36:07
 * @version 1.0
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthVerify {

	/**
	 * 权限代码
	 * 
	 * @Title: auth
	 * @return 权限代码
	 * @date 2011-11-23 下午3:37:16
	 * @author tonny
	 * @version 1.0
	 */
	AuthType value();

	/**
	 * 特殊权限
	 * 
	 * @return
	 */
	String special() default "";
}
