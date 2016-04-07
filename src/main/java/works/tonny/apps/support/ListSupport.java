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
public @interface ListSupport {

	String SHOULD = " or ";

	String MUST = " and ";

	String EQUALS = " =:";

	String NOT_NULL = " is not null";

	String NULL = " is null";

	String NOT_EQUALS = " <>:";

	String LESS = " <:";

	String GREATER = " >:";

	String LESS_EQUALS = " <=:";

	String GREATER_EQUALS = " >=:";

	String IN = " in :";

	String IN_ELEMENTS = " in elements(#FIELD)";

	String LIKE = " like :";

	String LLIKE = " like :";

	String RLIKE = " like :";

	String BETWEEN = " between :param1 and :param2 ";

	String BETWEEN_N_L = " >:param1 and $column <=:param2 ";

	String BETWEEN_N_R = " >=:param1 and $column <:param2 ";

	String BETWEEN_N_L_R = " >:param1 and $column <:param2 ";

	/**
	 * 参数
	 * 
	 * @Title: field
	 * @Description:
	 * @param @return 设定文件
	 * @return String[] 返回类型
	 * @throws
	 */
	String[] field() default {};

	/**
	 * 逻辑链接符 SHOULD MUST.第一个参数必须为must
	 * 
	 * @return
	 */
	String[] connector() default {};

	/**
	 * 操作符，= like <> 等
	 * 
	 * @return
	 */
	String[] operator() default {};

	/**
	 * 排序字段
	 * 
	 * @return
	 */
	String[] order() default {};

	/**
	 * 默认值
	 * 
	 * @return
	 */
	String[] defaults() default {};

	/**
	 * 支持offset方式分页，默认使用page方式分页
	 */
	boolean offsetSupport() default false;

}
