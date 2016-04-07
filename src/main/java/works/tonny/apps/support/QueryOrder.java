/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-18 下午4:30:54
 * @author tonny
 */
package works.tonny.apps.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryOrder {
	/**
	 * 排序的名称
	 * 
	 * @return
	 * @author tonny
	 */
	String name() default "";
	
	/**
	 * 序号，0开始的整数
	 * @return
	 */
	int order() default 0;
}
