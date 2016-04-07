/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-18 下午4:31:57
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
 * Query对象字段的支持
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCriteria {
	String TYPE_TEXT = "text";

	String TYPE_DATE = "date";

	String TYPE_DATETIME = "datetime";

	String TYPE_SELECT = "select";

	String TYPE_CHECKBOX = "checkbox";

	String TYPE_BOOLEAN = "boolean";

	/**
	 * 显示名称
	 * 
	 * @return
	 * @author tonny
	 */
	String name() default "";

	/**
	 * 预置的值，有值只能从中选择
	 * 
	 * @return
	 * @author tonny
	 */
	String[] values() default {};

	/**
	 * 预置值的名称显示用
	 */
	String[] valueNames() default {};

	/**
	 * 
	 * @return
	 */
	int order() default 0;

	/**
	 * 显示类型:text 文本,select 下拉列表,checkbox 多选,boolean 布儿
	 * 
	 * @return
	 * @author tonny
	 */
	String type() default TYPE_TEXT;

}
