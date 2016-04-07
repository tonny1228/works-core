/**  
 * @Title: ListSupport.java
 * @Package works.tonny.framework.dao
 * @author Tonny
 * @date 2011-10-14 下午4:55:19
 */
package works.tonny.apps.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ListSupport
 * @Description:
 * @author Tonny
 * @date 2011-10-14 下午4:55:19
 * @version 1.0
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLMappingListSupport {

	/**
	 * 查询名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 追加名
	 * 
	 * @Title: field
	 * @Description:
	 * @return String[] 返回类型
	 * @throws
	 */
	String[] appender() default {};

	/**
	 * 参数名称，用于findByNamedParam查询
	 * 
	 * @return
	 */
	String[] paramNames() default {};

	/**
	 * 支持offset方式分页，默认使用page方式分页
	 */
	boolean offsetSupport() default false;

}
